package com.plant.database.service.serviceImpl;

import com.alibaba.fastjson.JSONObject;
import com.plant.database.common.Config;
import com.plant.database.common.Consts;
import com.plant.database.mapper.NoteFormNoteImageMapper;
import com.plant.database.mapper.NoteImageMapper;
import com.plant.database.mapper.PlantDataBeanMapper;
import com.plant.database.model.bean.*;
import com.plant.database.model.dto.PlantDataBean;
import com.plant.database.service.PlantDataService;
import com.plant.database.util.XmlUtil;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.params.CommonParams;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.eclipse.jetty.util.StringUtil;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

import java.io.IOException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * PlantDataServiceImpl
 *
 * @author 18044703
 * @date 2020/5/15
 */
@SuppressWarnings("Duplicates")
@Service("plantDataService")
public class PlantDataServiceImpl implements PlantDataService {

    private static final String IMAGE = "image";
    private static final String TEXT = "text";
    private static final String VALUE = "value";
    private static final String NONE = "none";
    private final Config config;

    private final ApplicationContext context;

    private final SolrClient solrClient;

    private final PlantDataBeanMapper plantDataBeanMapper;

    private final NoteFormNoteImageMapper noteFormNoteImageMapper;

    private final NoteImageMapper noteImageMapper;

    public PlantDataServiceImpl(Config config, ApplicationContext context, SolrClient solrClient, PlantDataBeanMapper plantDataBeanMapper, NoteFormNoteImageMapper noteFormNoteImageMapper, NoteImageMapper noteImageMapper) {
        this.config = config;
        this.context = context;
        this.solrClient = solrClient;
        this.plantDataBeanMapper = plantDataBeanMapper;
        this.noteFormNoteImageMapper = noteFormNoteImageMapper;
        this.noteImageMapper = noteImageMapper;
    }

    @Override
    @SuppressWarnings("all")
    public boolean transformDataToMysql() {

        try {
            //get all data from xml files;
            Map<Class, List<Object>> stringListMap = XmlUtil.convertToJavaBean(config);

            stringListMap.forEach((key , value) -> {
                //insert into database
                String simpleName = key.getSimpleName();
                String mapperName = simpleName + "Mapper";
                try {
                    Class<?> aClass = Class.forName("com.plant.database.mapper." + mapperName);
                    Object bean = context.getBean(aClass);
                    if (bean instanceof Mapper) {
                        //multi thread insert
                        value.parallelStream().forEach(((Mapper) bean)::insert);
                    }
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            });

            return true;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    @SuppressWarnings("all")
    public boolean transformDataToSolr() {

        //get all data from xml files;
        try {
            Map<Class, List<Object>> metaDataMap = XmlUtil.convertToJavaBean(config);


            List<Object> noteList = metaDataMap.get(Note.class);

            //get itemMap
            Map<String, List<Note>> itemNotesMap = noteList.stream()
                    .map(x -> (Note) x)
                    .collect(Collectors.groupingBy(Note::getItemId));
            //get noteTypeMap
            List<Object> noteTypeList = metaDataMap.get(NoteType.class);
            Map<String, NoteType> noteTypeMap = noteTypeList.stream()
                    .map(x -> (NoteType) x)
                    .collect(Collectors.toMap(NoteType::getNoteTypeId, Function.identity(), (x, y) -> {
                        System.err.println("duplicate key : " + x);
                        return x;
                    }));
            //get noteClassMap
            List<Object> noteClassList = metaDataMap.get(NoteClass.class);
            Map<String, NoteClass> noteClassMap = noteClassList.stream()
                    .map(x -> (NoteClass) x)
                    .collect(Collectors.toMap(NoteClass::getNoteClassId, Function.identity()));
            //get noteImageMap
            List<Object> noteFormImageList = metaDataMap.get(NoteFormNoteImage.class);
            Map<String, List<NoteFormNoteImage>> noteFormNoteImageMap = noteFormImageList.stream()
                    .map(x -> (NoteFormNoteImage) x)
                    .collect(Collectors.groupingBy(NoteFormNoteImage::getNoteFormId));
            //get noteFormMap key -noteId
            List<Object> noteFormList = metaDataMap.get(NoteForm.class);
            Map<String, NoteForm> noteFormMap = noteFormList.stream()
                    .map(x -> (NoteForm) x)
                    .collect(Collectors.toMap(NoteForm::getNoteId, Function.identity()));

            //get itemTypeMap
            List<Object> itemTypeList = metaDataMap.get(ItemType.class);
            Map<String, ItemType> itemTypeMap = itemTypeList.stream()
                    .map(x -> (ItemType) x)
                    .collect(Collectors.toMap(ItemType::getItemTypeId, Function.identity()));

            //get termUseMap key itemId
            List<Object> termUseList = metaDataMap.get(TermUse.class);
            Map<String, List<TermUse>> termUseMap = termUseList.stream()
                    .map(x -> (TermUse) x)
                    .collect(Collectors.groupingBy(TermUse::getItemId));
            //get termMap
            List<Object> termList = metaDataMap.get(Term.class);
            Map<String, Term> termMap = termList.stream()
                    .map(x -> (Term) x)
                    .collect(Collectors.toMap(Term::getTermId, Function.identity()));
            //get itemMap
            Map<String, Item> itemMap = metaDataMap.get(Item.class).stream()
                    .map(x -> (Item) x)
                    .collect(Collectors.toMap(Item::getItemId, Function.identity()));
            //get relationshipMap
            List<Object> relationshipList = metaDataMap.get(Relationship.class);
            Map<String, List<Relationship>> relationshipMap = relationshipList.stream()
                    .map(x -> (Relationship) x)
                    //raw data is not completed, need filter null data
                    .filter(relationship -> itemMap.get(relationship.getSecondItemId()) != null)
                    .collect(Collectors.groupingBy(Relationship::getFirstItemId));

            List<Object> itemList = metaDataMap.get(Item.class);

            //convert data to the format required by solr
            itemList.parallelStream()
                    .map(x-> (Item)x)
                    .forEach(item -> {
                        HashMap<String, String> paramsMap = new HashMap<>(64);
                        paramsMap.put(Consts.ITEM_ID, item.getItemId());
                        String displayTitle = item.getDisplayTitle();
                        try {
                            displayTitle = DocumentHelper.parseText(displayTitle).getRootElement().getStringValue();
                        } catch (DocumentException e) {
                            e.printStackTrace();
                        }
                        paramsMap.put(Consts.ITEM_TITLE, displayTitle);
                        paramsMap.put(Consts.ITEM_TYPE,itemTypeMap.get(item.getItemTypeId()).getTitle());
                        List<TermUse> termUses = termUseMap.get(item.getItemId());
                        if (termUses != null) {
                            String terms = termUses.stream()
                                    .map(TermUse::getTermId)
                                    .map(termMap::get)
                                    .map(Term::getLabel)
                                    .collect(Collectors.joining(","));
                            paramsMap.put(Consts.ITEM_TERMS, terms);
                        }
                        //get topic
                        List<Relationship> relationships = relationshipMap.get(item.getItemId());
                        if (relationships != null) {
                            String topics = relationships.stream()
                                    .map(Relationship::getSecondItemId)
                                    .map(itemMap::get)
                                    .map(x -> {
                                        String temp = x.getDisplayTitle();
                                        try {
                                            return DocumentHelper.parseText(temp).getRootElement().getStringValue();
                                        } catch (DocumentException e) {
                                            e.printStackTrace();
                                            return null;
                                        }
                                    })
                                    .filter(Objects::nonNull)
                                    .collect(Collectors.joining(","));
                            paramsMap.put(Consts.ITEM_TOPICS, topics);
                        }

                        List<Note> notes = itemNotesMap.get(item.getItemId());
                        if (notes != null && notes.size() > 0) {
                            //group notes by noteType
                            Map<String, List<Note>> noteListByNoteType = notes.stream().collect(Collectors.groupingBy(Note::getNoteTypeId));
                            //traverse to get the fields that need to be indexed under the note
                            noteListByNoteType.forEach((typeId, subNotes) -> {
                                String noteTypeTitle = noteClassMap.get(noteTypeMap.get(typeId).getNoteClassId()).getTitle().toLowerCase();
                                if (IMAGE.equals(noteTypeTitle)) {
                                    //image caption
                                    subNotes.forEach( note -> {
                                        String temp = noteTypeMap.get(note.getNoteTypeId()).getTitle();
                                        List<NoteFormNoteImage> noteImages = noteFormNoteImageMap.get(noteFormMap.get(note.getNoteId()).getNoteFormId());
                                        //remove space in string
                                        String trimTemp = removeSpace(temp);
                                        //noteImage
                                        paramsMap.put("note_" + trimTemp,noteImages.get(0).getCaption());
                                    });

                                } else if (TEXT.equals(noteTypeTitle) || VALUE.equals(noteTypeTitle)) {
                                    subNotes.forEach(note -> {
                                        String content = noteFormMap.get(note.getNoteId()).getContent();
                                        if (null != content) {
                                            try {
                                                content = DocumentHelper.parseText(content).getRootElement().getStringValue();
                                            } catch (DocumentException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                        String temp = noteTypeMap.get(note.getNoteTypeId()).getTitle();
                                        String trimTemp = removeSpace(temp);
                                        paramsMap.put("note_" + trimTemp,content);
                                    });
                                } else {
                                    System.err.println("wrong");
                                }
                            });

                        }
                        //inset data to solr
                        putDataToSolr(paramsMap);
                    });



        } catch (ClassNotFoundException e) {
            System.err.println("no class found");
            e.printStackTrace();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return false;
    }

    @Override
    public Response searchAll(JSONObject searchParams) {

        Integer page = Integer.valueOf(searchParams.get("page").toString());
        String text = searchParams.get("text").toString();
        if (null == text || "".equals(text.trim())) {
            text = "*";
        }

        String queryString = Consts.ALL_TEXT + ":(" + prePareParam(text) + ")";

        SolrQuery query = getQuery(page, queryString, searchParams.get(Consts.SEARCH_SCOPE).toString());

        return getResponse(page,query);
    }

    private String prePareParam(String str) {
        String s = str.replaceAll("[+&]", " AND ");
        return s;
    }

    @Override
    public Response searchByTopic(JSONObject searchParams) {
        Integer page = Integer.valueOf(searchParams.get("page").toString());
        String text = searchParams.get("text").toString();
        String topic = searchParams.get("topic").toString();
        if (null == text || "".equals(text.trim())) {
            text = "*";
        }

        String queryString = Consts.ALL_TEXT + ":(" + prePareParam(text) + ") AND item_topics:\"" + topic + "\"";
        SolrQuery query = getQuery(page, queryString, searchParams.get(Consts.SEARCH_SCOPE).toString());

        return getResponse(page,query);
    }

    @Override
    public Response searchByField(JSONObject searchParams) {
        Integer page = Integer.valueOf(searchParams.get("page").toString());
        String field1 = searchParams.get("field1").toString();
        String field2 = searchParams.get("field2").toString();
        String field3 = searchParams.get("field3").toString();
        String text1 = searchParams.get("text1").toString();
        String text2 = searchParams.get("text2").toString();
        String text3 = searchParams.get("text3").toString();
        if (null == text1 || "".equals(text1.trim())) {
            text1 = "*";
            text2 = "*";
            text3 = "*";

        }
        String prefix = "note_";
        StringBuilder queryParam = new StringBuilder();
        String queryString = "*:*";
        queryParam.append(queryString);
        if (!StringUtil.isBlank(field1) && !NONE.equals(field1)) {
            queryString =" AND "+ prefix + removeSpace(field1) +":(" + prePareParam(text1)+")";
            queryParam.append(queryString);
        }
        if (!StringUtil.isBlank(field2) && !NONE.equals(field2)) {
            if (null == text2 || "".equals(text2.trim())) {
                text2 = "*";
            }
            queryString =" AND "+ prefix + removeSpace(field2) +":(" + prePareParam(text2)+ ")";
            queryParam.append(queryString);
        }
        if (!StringUtil.isBlank(field3) && !NONE.equals(field3)) {
            if (null == text3 || "".equals(text3.trim())) {
                text3 = "*";
            }
            queryString =" AND "+ prefix + removeSpace(field3) +":(" + prePareParam(text3) +")";
            queryParam.append(queryString);
        }
        SolrQuery query = getQuery(page, queryParam.toString(),searchParams.get(Consts.SEARCH_SCOPE).toString());

        return getResponse(page,query);
    }

    @Override
    public Response getPlant(String itemId) {
        List<PlantDataBean> plants = plantDataBeanMapper.getPlants(itemId);
        if (plants.size() == 0) {
            return new Response();
        }
        PlantDataBean plantDataBean = plants.get(0);
        if (plantDataBean.getDisplayOrder() == 1 && "IMAGE".equals(plantDataBean.getNoteClass().toUpperCase())) {
            //if have image info
            List<NoteFormNoteImage> images = noteFormNoteImageMapper.selectByNoteFormId(plantDataBean.getNoteFormId());
            //get image path
            if (images.size() > 0) {
                List<NoteImage> noteImageList = noteImageMapper.selectByNoteImageIds(images.stream().map(NoteFormNoteImage::getNoteImageId).collect(Collectors.toList()));
                JSONObject noteImages = new JSONObject();
                noteImages.put("caption", images.get(0).getCaption());
                noteImages.put("images", noteImageList.stream().map(NoteImage::getExternalId));
                plantDataBean.setNoteImages(noteImages);
            }
        }
        //get LinkWords from solr
        SolrQuery query = new SolrQuery();
        query.setRows(1)
            .setStart(0)
            .setQuery(Consts.ITEM_ID + ":" + itemId)
            .set(CommonParams.FL,Consts.ITEM_TERMS);

        try {
            QueryResponse queryResponse = solrClient.query(query);
            queryResponse.getResults().forEach(x -> {
                if (x.containsKey(Consts.ITEM_TERMS)){
                    PlantDataBean temp = new PlantDataBean();
                    temp.setDisplayOrder(999);
                    temp.setNoteClass("Text");
                    temp.setTitle(Consts.LINK_WORDS);
                    temp.setContent(x.get(Consts.ITEM_TERMS).toString());
                    plants.add(temp);
                }
            });
        } catch (SolrServerException | IOException e) {
            e.printStackTrace();
        }


        Response response = new Response();
        response.setDetail(plants);
        return response;
    }

    private void putDataToSolr(HashMap<String, String> paramsMap) {
        SolrInputDocument solrInputFields = new SolrInputDocument();
        String itemTopics = paramsMap.get(Consts.ITEM_TOPICS);
        if (null != itemTopics) {
            Arrays.asList(itemTopics.split(",")).forEach(x -> solrInputFields.addField(Consts.ITEM_TOPICS, x));
            paramsMap.remove(Consts.ITEM_TOPICS);
        }
        paramsMap.forEach(solrInputFields::addField);

        try {
            solrClient.add(solrInputFields);
            solrClient.commit();
        } catch (SolrServerException | IOException e) {
            e.printStackTrace();
        }
    }

    private String removeSpace(String temp) {
        return temp.replaceAll(" ","");
    }

    private Response getResponse(Integer page,SolrQuery query) {

        QueryResponse result = null;
        try {
            result = solrClient.query(query);
        } catch (SolrServerException | IOException e) {
            e.printStackTrace();
        }


        Response response = new Response();
        if (result != null){
            long total = result.getResults().getNumFound();
            JSONObject temp = new JSONObject();
            temp.put("totalRecords",total);
            temp.put("result", result.getResults());
            temp.put("page",page + 1);
            response.setDetail(temp);
        }
        return response;
    }

    private SolrQuery getQuery(Integer page, String queryString, String scope) {
        SolrQuery query = new SolrQuery();

        if (Consts.SCOPE_PLANTS.equals(scope)) {
            query.addFilterQuery("-" + Consts.ITEM_TYPE + ":Reference");
        } else if (Consts.SCOPE_REFERENCE.equals(scope)) {
            query.addFilterQuery(Consts.ITEM_TYPE + ":Reference");
        }


        //start page
        query.setStart(page * 10)
                //results per page
                .setRows(10)
                // the query word
                .setQuery(queryString)
                //sort by item_id asc
                .setSort(Consts.ITEM_ID, SolrQuery.ORDER.asc)
                //only return field :item_id
                .set(CommonParams.FL, Consts.ITEM_ID, Consts.ITEM_TITLE);
        return query;
    }
}
