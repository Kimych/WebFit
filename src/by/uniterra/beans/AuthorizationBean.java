package by.uniterra.beans;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;

import by.uniterra.dai.eao.AuthorizationEAO;
import by.uniterra.dai.entity.Authorization;
import by.uniterra.dai.entity.Worker;
import by.uniterra.system.model.SystemModel;
import by.uniterra.system.util.DateUtils;
import by.uniterra.system.util.WorkLogUtils;
import by.uniterra.udi.model.WorkLogInfoHelper;
import by.uniterra.udi.model.WorkLogInfoHolder;
import by.uniterra.udi.util.Cryptor;

@ManagedBean(name = "authorizationBean")
public class AuthorizationBean implements Serializable
{

    /** TODO document <code>serialVersionUID</code> */
    private static final long serialVersionUID = 1L;
    
    String login;
    String password;
    Worker objWorfer;

    // from info holder
    String lastUpdateDate;
    String ÒurentTime;
    String toPlane;
    String toBonus;
    String timeLeft;
    
    WorkLogInfoHolder objInfoHelper;
    
    

    public String getLogin()
    {
        return login;
    }

    public void setLogin(String login)
    {
        this.login = login;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getObjWorfer()
    {
        return doLogin();
    }

    public void setObjWorfer(Worker objWorfer)
    {
        this.objWorfer = objWorfer;
    }

    public Worker getWorker()
    {
        return this.objWorfer;

    }

    public String getLastUpdateDate()
    {
        return DateUtils.toGMT(objInfoHelper.getLastUpdateDate());
    }

    public void setLastUpdateDate(String lastUpdateDate)
    {
        this.lastUpdateDate = lastUpdateDate;
    }

    public String get—urentTime()
    {
        return objInfoHelper.getCurentTime();
    }

    public void set—urentTime(String ÒurentTime)
    {
        this.ÒurentTime = ÒurentTime;
    }

    public String getToPlane()
    {
        return WorkLogUtils.roundToString(objInfoHelper.getToPlane(), 2, BigDecimal.ROUND_HALF_UP);
    }

    public void setToPlane(String toPlane)
    {
        this.toPlane = toPlane;
    }

    public String getToBonus()
    {
        return WorkLogUtils.roundToString(objInfoHelper.getToBonus(), 2, BigDecimal.ROUND_HALF_UP);
    }

    public void setToBonus(String toBonus)
    {
        this.toBonus = toBonus;
    }

    public String getTimeLeft()
    {
        return WorkLogUtils.roundToString(objInfoHelper.getTimeLeft(), 0, BigDecimal.ROUND_HALF_UP);
    }

    public void setTimeLeft(String timeLeft)
    {
        this.timeLeft = timeLeft;
    }

    @PostConstruct
    public String doLogin()
    {
        objWorfer = null;
        objInfoHelper = null;

        SystemModel.initJPA();
        AuthorizationEAO autEAO = new AuthorizationEAO(SystemModel.getDefaultEM());
        Authorization auth = checkLogin(login, password, autEAO);
        if (auth != null)
        {
            this.objWorfer = auth.getWorker();
            this.objInfoHelper = WorkLogInfoHelper.getLogListUpToDateAndWorker(objWorfer, new Date());
            return objWorfer.toString();
        }
        else
        {
            objInfoHelper = new WorkLogInfoHolder("data not foud", 0, 0, 0, null, null, 0);
            return "User not found!";
        }

    }
    
    
    private Authorization checkLogin(String strUserName, String strPassword, AuthorizationEAO autEAO)
    {
        Authorization authUser = autEAO.getAuthorizationByLoginAndPassword(strUserName, Cryptor.getSecureString(strPassword));
        if (authUser != null)
        {

            return authUser;
        }
        else
        {
            return null;
        }
    }


}
