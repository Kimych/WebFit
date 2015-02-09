package com.webfit;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import by.uniterra.dai.eao.DaysOfWorkEAO;
import by.uniterra.dai.entity.DaysOfWork;
import by.uniterra.system.model.SystemModel;

@ManagedBean(name = "userHistoryBean")
@SessionScoped
public class UserHistoryBean
{
    
    private List<DaysOfWork> lslDaysOfWorks = new ArrayList<DaysOfWork>();

    public UserHistoryBean()
    {
        DaysOfWorkEAO dofEAO = new DaysOfWorkEAO(SystemModel.getDefaultEM());
        this.lslDaysOfWorks = dofEAO.finAllByWorker(SystemModel.getAuthorization().getWorker());
    }
    
    public List<DaysOfWork> getLslDaysOfWorks()
    {
        return lslDaysOfWorks;
    }
    public void setLslDaysOfWorks(List<DaysOfWork> lslDaysOfWorks)
    {
        this.lslDaysOfWorks = lslDaysOfWorks;
    }
    
}
