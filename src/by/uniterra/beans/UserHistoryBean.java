package by.uniterra.beans;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import by.uniterra.dai.eao.DaysOfWorkEAO;
import by.uniterra.dai.entity.DaysOfWork;
import by.uniterra.system.model.SystemModel;
import by.uniterra.system.util.DateUtils;
import by.uniterra.system.util.WorkLogUtils;

@ManagedBean(name = "userHistoryBean")
@ViewScoped
public class UserHistoryBean implements Serializable
{

    /** TODO document <code>serialVersionUID</code> */
    private static final long serialVersionUID = 1L;

    private List<UserHistoryData> lstUserHistory;
    
    @ManagedProperty(value="#{authorizationBean}")
    private AuthorizationBean authorizationBean;
    
    public AuthorizationBean getAuthorizationBean()
    {
        return authorizationBean;
    }

    public void setAuthorizationBean(AuthorizationBean authorizationBean)
    {
        this.authorizationBean = authorizationBean;
    }
    

    public List<UserHistoryData> getLstUserHistory()
    {
        lstUserHistory = new ArrayList<UserHistoryData>();
        DaysOfWorkEAO dofEAO = new DaysOfWorkEAO(SystemModel.getDefaultEM());
       /* List<DaysOfWork> lslDaysOfWorks = dofEAO.finAllByWorker(SystemModel.getAuthorization().getWorker());*/
        List<DaysOfWork> lslDaysOfWorks = dofEAO.finAllByWorker(authorizationBean.getWorker());
        // sort in descending order
        Collections.sort(lslDaysOfWorks, Collections.reverseOrder());
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
        Collections.reverse(lstUserHistory);
        return "";
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