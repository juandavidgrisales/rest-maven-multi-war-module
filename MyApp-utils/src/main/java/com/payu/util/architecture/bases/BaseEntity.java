package com.payu.util.architecture.bases;

import java.io.Serializable;

import javax.persistence.MappedSuperclass;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

@MappedSuperclass
public class BaseEntity implements Serializable{

    private static final long serialVersionUID = 4353462829180382382L;

//    public static final String FIEL_ID = "id";
//    public static final String FIEL_DESCRIPTION = "description";
//    public static final String FIELD_STATUS_ID = "statusId";
//    public static final String FIELD_STATUS_REASON = "statusReason";
//    public static final String FIELD_CREATION_DATE = "crDate";
//    public static final String FIELD_CREATION_USER = "crUser";
//    public static final String FIELD_UPDATE_DATE = "upDate";
//    public static final String FIELD_UPDATE_USER = "upUser";
//
//
//    /**
//     * The status Default value is VISIBLE (1L)
//     */
//    @Basic(optional = false)
//    @Column(name = "STATUS_ID")
//    private boolean statusId = true;
//
//    /**
//     * The status
//     */
//    @Column(name = "STATUS_REASON")
//    private String statusReason = null;
//
//    /**
//     * The creation date
//     */
//    @Column(name = "CR_DATE")
//    @Temporal(TemporalType.TIMESTAMP)
//    private Calendar crDate = Calendar.getInstance();
//
//    /**
//     * The name of the user who created the data
//     */
//    @Basic(optional = false)
//    @Column(name = "CR_USER", nullable = false, updatable = false)
//    private String crUser = "";
//
//    /**
//     * The date of the last update on data
//     */
//    @Column(name = "UP_DATE")
//    @Temporal(TemporalType.TIMESTAMP)
//    private Calendar upDate = null;
//
//    /**
//     * The name of the user who performed the last data update
//     */
//    @Column(name = "UP_USER", nullable = false)
//    private String upUser = null;
//
//
//    public boolean getStatusId() {
//        return statusId;
//    }
//
//    public void setStatusId(boolean statusId) {
//        this.statusId = statusId;
//    }
//
//    public String getStatusReason() {
//        return statusReason;
//    }
//
//    public void setStatusReason(String statusReason) {
//        this.statusReason = statusReason;
//    }
//
//    public Calendar getCrDate() {
//        return crDate;
//    }
//
//    public void setCrDate(Date crDate) {
//        this.crDate = Calendar.getInstance();
//        this.crDate.setTime(crDate);
//    }
//
//    public String getCrUser() {
//        return crUser;
//    }
//
//    public void setCrUser(String crUser) {
//        this.crUser = crUser;
//    }
//
//    public Calendar getUpDate() {
//        return upDate;
//    }
//
//    public void setUpDate(Date upDate) {
//        this.upDate = Calendar.getInstance();
//        this.upDate.setTime(upDate);
//    }
//
//    public String getUpUser() {
//        return upUser;
//    }
//
//    public void setUpUser(String upUser) {
//        this.upUser = upUser;
//    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}

