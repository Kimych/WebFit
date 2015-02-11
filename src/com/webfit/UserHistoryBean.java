package com.webfit;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import by.uniterra.dai.eao.DaysOfWorkEAO;
import by.uniterra.dai.entity.DaysOfWork;
import by.uniterra.system.model.SystemModel;
import by.uniterra.system.util.DateUtils;
import by.uniterra.system.util.WorkLogUtils;

@ManagedBean(name = "userHistoryBean")
@SessionScoped
public class UserHistoryBean implements Serializable
{

    /** TODO document <code>serialVersionUID</code> */
    private static final long serialVersionUID = 1L;

    private List<UserHistoryData> lstUserHistory;
    private boolean sortAscending = true;

    public List<UserHistoryData> getLstUserHistory()
    {
        lstUserHistory = new ArrayList<UserHistoryData>();
        DaysOfWorkEAO dofEAO = new DaysOfWorkEAO(SystemModel.getDefaultEM());
        List<DaysOfWork> lslDaysOfWorks = dofEAO.finAllByWorker(SystemModel.getAuthorization().getWorker());
        for (DaysOfWork daysOfWork : lslDaysOfWorks)
        {
            lstUserHistory.add(new UserHistoryData(daysOfWork));
        }
        return lstUserHistory;
    }

    public void setLstUserHistory(List<UserHistoryData> lslUSerHistory)
    {
        this.lstUserHistory = lslUSerHistory;
    }

    public String sortByTimestamp()
    {

        if (sortAscending)
        {
            Collections.sort(lstUserHistory, new Comparator<UserHistoryData>()
            {

                @Override
                public int compare(UserHistoryData objUserHistory1, UserHistoryData objUserHistory2)
                {

                    return objUserHistory1.getTimestamp().compareTo(objUserHistory2.getTimestamp());

                }

            });
            sortAscending = false;

        }
        else
        {
            // descending order
            Collections.sort(lstUserHistory, new Comparator<UserHistoryData>()
            {

                @Override
                public int compare(UserHistoryData objUserHistory1, UserHistoryData objUserHistory2)
                {

                    return objUserHistory2.getTimestamp().compareTo(objUserHistory1.getTimestamp());

                }

            });
            sortAscending = true;
        }

        return null;
    }

    public static class UserHistoryData
    {
        String timestamp;
        String worklog;
        String bonusTime;
        int aktualWorkedDays;

        public UserHistoryData(DaysOfWork objDoW)
        {
            this.timestamp = DateUtils.toGMT(objDoW.getTimestamp());
            this.worklog = WorkLogUtils.roundToString(objDoW.getWorklog(), 2, BigDecimal.ROUND_HALF_UP);
            this.bonusTime = WorkLogUtils.roundToString(objDoW.getBonusTime(), 2, BigDecimal.ROUND_HALF_UP);
            this.aktualWorkedDays = objDoW.getAktualWorkedDays();
        }

        public String getTimestamp()
        {
            return timestamp;
        }

        public void setTimestamp(String timestamp)
        {
            this.timestamp = timestamp;
        }

        public String getWorklog()
        {
            return worklog;
        }

        public void setWorklog(String worklog)
        {
            this.worklog = worklog;
        }

        public String getBonusTime()
        {
            return bonusTime;
        }

        public void setBonusTime(String bonusTime)
        {
            this.bonusTime = bonusTime;
        }

        public int getAktualWorkedDays()
        {
            return aktualWorkedDays;
        }

        public void setAktualWorkedDays(int aktualWorkedDays)
        {
            this.aktualWorkedDays = aktualWorkedDays;
        }
    }

}
