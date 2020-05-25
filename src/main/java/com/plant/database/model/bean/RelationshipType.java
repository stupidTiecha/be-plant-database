package com.plant.database.model.bean;

/**
 * RelationshipType
 *
 * @author 18044703
 * @date 2020/5/15
 */
public class RelationshipType {

    private String relationshipTypeId;

    private String title;

    private String relationshipTypeInverse;

    public String getRelationshipTypeId() {
        return relationshipTypeId;
    }

    public void setRelationshipTypeId(String relationshipTypeId) {
        this.relationshipTypeId = relationshipTypeId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRelationshipTypeInverse() {
        return relationshipTypeInverse;
    }

    public void setRelationshipTypeInverse(String relationshipTypeInverse) {
        this.relationshipTypeInverse = relationshipTypeInverse;
    }
}
