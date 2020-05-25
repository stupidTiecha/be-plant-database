package com.plant.database.model.bean;

/**
 * Response
 *
 * @author 18044703
 * @date 2020/5/15
 */
public class Response {

    /**
     * result
     */
    private int result;

    /**
     * result note
     */
    private String resultNote;

    /**
     * result detail
     */
    private Object detail;

    public Response() {
    }

    public Response (String resultNote) {
        this.resultNote = resultNote;
    }

    public Response (Object detail) {
        this.detail = detail;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getResultNote() {
        return resultNote;
    }

    public void setResultNote(String resultNote) {
        this.resultNote = resultNote;
    }

    public Object getDetail() {
        return detail;
    }

    public void setDetail(Object detail) {
        this.detail = detail;
    }
}
