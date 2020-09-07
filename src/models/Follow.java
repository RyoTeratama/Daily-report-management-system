package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Follow {
    @Id
    @Column(name = "emp_id" ,  nullable = false)
    private Integer emp_id;

    @Column(name = "follow_id" ,  nullable = false)
    private Integer follow_id;


    public Integer getEmpId() {
        return emp_id;
    }

    public void setEmp_id(Integer emp_id) {
        this.emp_id = emp_id;
    }
    public Integer getFollowId() {
        return follow_id;
    }
    public void setFollow_id(Integer follow_id) {
        this.follow_id = follow_id;
    }
}