/*
 * Kontur.Extern.Api.Public
 * No description provided (generated by Swagger Codegen https://github.com/swagger-api/swagger-codegen)
 *
 * OpenAPI spec version: v1
 * 
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */


package ru.skbkontur.sdk.extern.rest.swagger.model;

import java.util.Objects;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.IOException;
import ru.skbkontur.sdk.extern.rest.swagger.model.CreditOrganizationInfo;
import ru.skbkontur.sdk.extern.rest.swagger.model.EmployeeInfo;
import ru.skbkontur.sdk.extern.rest.swagger.model.SeparateDepartmentInfo;

/**
 * OrganizationInfo
 */

public class OrganizationInfo {
  @SerializedName("kpp")
  private String kpp = null;

  @SerializedName("kpp-object")
  private String kppObject = null;

  @SerializedName("kpp-another")
  private String kppAnother = null;

  @SerializedName("okopf")
  private String okopf = null;

  @SerializedName("okfs")
  private String okfs = null;

  @SerializedName("bookkeeper")
  private EmployeeInfo bookkeeper = null;

  @SerializedName("separate-department")
  private SeparateDepartmentInfo separateDepartment = null;

  @SerializedName("credit-organization")
  private CreditOrganizationInfo creditOrganization = null;

  public OrganizationInfo kpp(String kpp) {
    this.kpp = kpp;
    return this;
  }

   /**
   * Get kpp
   * @return kpp
  **/
  @ApiModelProperty(value = "")
  public String getKpp() {
    return kpp;
  }

  public void setKpp(String kpp) {
    this.kpp = kpp;
  }

  public OrganizationInfo kppObject(String kppObject) {
    this.kppObject = kppObject;
    return this;
  }

   /**
   * Get kppObject
   * @return kppObject
  **/
  @ApiModelProperty(value = "")
  public String getKppObject() {
    return kppObject;
  }

  public void setKppObject(String kppObject) {
    this.kppObject = kppObject;
  }

  public OrganizationInfo kppAnother(String kppAnother) {
    this.kppAnother = kppAnother;
    return this;
  }

   /**
   * Get kppAnother
   * @return kppAnother
  **/
  @ApiModelProperty(value = "")
  public String getKppAnother() {
    return kppAnother;
  }

  public void setKppAnother(String kppAnother) {
    this.kppAnother = kppAnother;
  }

  public OrganizationInfo okopf(String okopf) {
    this.okopf = okopf;
    return this;
  }

   /**
   * Get okopf
   * @return okopf
  **/
  @ApiModelProperty(value = "")
  public String getOkopf() {
    return okopf;
  }

  public void setOkopf(String okopf) {
    this.okopf = okopf;
  }

  public OrganizationInfo okfs(String okfs) {
    this.okfs = okfs;
    return this;
  }

   /**
   * Get okfs
   * @return okfs
  **/
  @ApiModelProperty(value = "")
  public String getOkfs() {
    return okfs;
  }

  public void setOkfs(String okfs) {
    this.okfs = okfs;
  }

  public OrganizationInfo bookkeeper(EmployeeInfo bookkeeper) {
    this.bookkeeper = bookkeeper;
    return this;
  }

   /**
   * Get bookkeeper
   * @return bookkeeper
  **/
  @ApiModelProperty(value = "")
  public EmployeeInfo getBookkeeper() {
    return bookkeeper;
  }

  public void setBookkeeper(EmployeeInfo bookkeeper) {
    this.bookkeeper = bookkeeper;
  }

  public OrganizationInfo separateDepartment(SeparateDepartmentInfo separateDepartment) {
    this.separateDepartment = separateDepartment;
    return this;
  }

   /**
   * Get separateDepartment
   * @return separateDepartment
  **/
  @ApiModelProperty(value = "")
  public SeparateDepartmentInfo getSeparateDepartment() {
    return separateDepartment;
  }

  public void setSeparateDepartment(SeparateDepartmentInfo separateDepartment) {
    this.separateDepartment = separateDepartment;
  }

  public OrganizationInfo creditOrganization(CreditOrganizationInfo creditOrganization) {
    this.creditOrganization = creditOrganization;
    return this;
  }

   /**
   * Get creditOrganization
   * @return creditOrganization
  **/
  @ApiModelProperty(value = "")
  public CreditOrganizationInfo getCreditOrganization() {
    return creditOrganization;
  }

  public void setCreditOrganization(CreditOrganizationInfo creditOrganization) {
    this.creditOrganization = creditOrganization;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    OrganizationInfo organizationInfo = (OrganizationInfo) o;
    return Objects.equals(this.kpp, organizationInfo.kpp) &&
        Objects.equals(this.kppObject, organizationInfo.kppObject) &&
        Objects.equals(this.kppAnother, organizationInfo.kppAnother) &&
        Objects.equals(this.okopf, organizationInfo.okopf) &&
        Objects.equals(this.okfs, organizationInfo.okfs) &&
        Objects.equals(this.bookkeeper, organizationInfo.bookkeeper) &&
        Objects.equals(this.separateDepartment, organizationInfo.separateDepartment) &&
        Objects.equals(this.creditOrganization, organizationInfo.creditOrganization);
  }

  @Override
  public int hashCode() {
    return Objects.hash(kpp, kppObject, kppAnother, okopf, okfs, bookkeeper, separateDepartment, creditOrganization);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class OrganizationInfo {\n");
    
    sb.append("    kpp: ").append(toIndentedString(kpp)).append("\n");
    sb.append("    kppObject: ").append(toIndentedString(kppObject)).append("\n");
    sb.append("    kppAnother: ").append(toIndentedString(kppAnother)).append("\n");
    sb.append("    okopf: ").append(toIndentedString(okopf)).append("\n");
    sb.append("    okfs: ").append(toIndentedString(okfs)).append("\n");
    sb.append("    bookkeeper: ").append(toIndentedString(bookkeeper)).append("\n");
    sb.append("    separateDepartment: ").append(toIndentedString(separateDepartment)).append("\n");
    sb.append("    creditOrganization: ").append(toIndentedString(creditOrganization)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
  
}

