package com.plant.database.model.bean;

/**
 * Relationship
 *
 * @author 18044703
 * @date 2020/5/15
 */
public class Relationship {

    private String relationshipId;

    private String relationshipTypeId;

    private String firstItemId;

    private String secondItemId;

    public String getRelationshipId() {
        return relationshipId;
    }

    public void setRelationshipId(String relationshipId) {
        this.relationshipId = relationshipId;
    }

    public String getRelationshipTypeId() {
        return relationshipTypeId;
    }

    public void setRelationshipTypeId(String relationshipTypeId) {
        this.relationshipTypeId = relationshipTypeId;
    }

    public String getFirstItemId() {
        return firstItemId;
    }

    public void setFirstItemId(String firstItemId) {
        this.firstItemId = firstItemId;
    }

    public String getSecondItemId() {
        return secondItemId;
    }

    public void setSecondItemId(String secondItemId) {
        this.secondItemId = secondItemId;
    }
}
