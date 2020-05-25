package com.plant.database.common;

/**
 * Consts
 *
 * @author 18044703
 * @date 2020/5/20
 */
public interface Consts {

    String SCOPE_REFERENCE = "3";
    String SCOPE_PLANTS = "2";
    String ITEM_ID = "item_id";
    String ITEM_TERMS = "item_terms";
    String ITEM_TITLE = "item_title";
    String ALL_TEXT = "all_text";
    String ITEM_TOPICS = "item_topics";
    String LINK_WORDS = "Link Words";
    String ITEM_TYPE = "item_type";

    interface SearchMode {

        int SEARCH_ALL = 0;
        int SEARCH_BY_TOPIC = 1;
        int SEARCH_BY_FIELD = 2;
    }

    String SEARCH_SCOPE = "scope";
}
