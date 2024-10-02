package org.example.practice.model;

public class Product {
    private String productUid;
    private String productType;
    private String name;
    private String url;
    private double unitPrice;
    private String unitPriceMeasure;
    private double unitPriceMeasureAmount;

    public Product(String productUid, String productType, String name, String url, double unitPrice, String unitPriceMeasure, double unitPriceMeasureAmount) {
        this.productUid = productUid;
        this.productType = productType;
        this.name = name;
        this.url = url;
        this.unitPrice = unitPrice;
        this.unitPriceMeasure = unitPriceMeasure;
        this.unitPriceMeasureAmount = unitPriceMeasureAmount;
    }

    public String getProductUid() {
        return productUid;
    }

    public void setProductUid(String productUid) {
        this.productUid = productUid;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getUnitPriceMeasure() {
        return unitPriceMeasure;
    }

    public void setUnitPriceMeasure(String unitPriceMeasure) {
        this.unitPriceMeasure = unitPriceMeasure;
    }

    public double getUnitPriceMeasureAmount() {
        return unitPriceMeasureAmount;
    }

    public void setUnitPriceMeasureAmount(double unitPriceMeasureAmount) {
        this.unitPriceMeasureAmount = unitPriceMeasureAmount;
    }
}
