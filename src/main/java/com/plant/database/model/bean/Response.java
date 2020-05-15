package com.plant.database.model.bean;

/**
 * Response
 *
 * @author chenjingyu
 * @date 2020/5/15
 */
public class Response {

    /**
     * 返回结果
     */
    private int result;

    /**
     * 结果描述
     */
    private String resultNote;

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
}
