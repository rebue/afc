package rebue.afc.svc.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.github.dozermapper.core.Mapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import rebue.afc.mapper.AfcWithdrawAccountBindFlowMapper;
import rebue.afc.mo.AfcWithdrawAccountBindFlowMo;
import rebue.afc.mo.AfcWithdrawAccountMo;
import rebue.afc.ro.AfcWithdrawAccountBindFlowRo;
import rebue.afc.svc.AfcWithdrawAccountBindFlowSvc;
import rebue.afc.svc.AfcWithdrawAccountSvc;
import rebue.robotech.dic.ResultDic;
import rebue.robotech.ro.Ro;
import rebue.robotech.svc.impl.MybatisBaseSvcImpl;
import rebue.suc.mo.SucUserMo;
import rebue.suc.svr.feign.SucUserSvc;

/**
 * 提现账户绑定流程
 *
 * 在单独使用不带任何参数的 @Transactional 注释时，
 * propagation(传播模式)=REQUIRED，readOnly=false，
 * isolation(事务隔离级别)=READ_COMMITTED，
 * 而且事务不会针对受控异常（checked exception）回滚。
 *
 * 注意：
 * 一般是查询的数据库操作，默认设置readOnly=true, propagation=Propagation.SUPPORTS
 * 而涉及到增删改的数据库操作的方法，要设置 readOnly=false, propagation=Propagation.REQUIRED
 *
 * @mbg.generated 自动生成的注释，如需修改本注释，请删除本行
 */
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
@Service
public class AfcWithdrawAccountBindFlowSvcImpl extends MybatisBaseSvcImpl<AfcWithdrawAccountBindFlowMo, java.lang.Long, AfcWithdrawAccountBindFlowMapper>
        implements AfcWithdrawAccountBindFlowSvc {

    /**
     * @mbg.generated 自动生成，如需修改，请删除本行
     */
    private static final Logger           _log = LoggerFactory.getLogger(AfcWithdrawAccountBindFlowSvcImpl.class);

    @Resource
    private AfcWithdrawAccountBindFlowSvc afcWithdrawAccountBindFlowSvc;

    @Resource
    private Mapper                        dozerMapper;

    @Resource
    private SucUserSvc                    sucUserSvc;

    @Resource
    private AfcWithdrawAccountSvc         afcWithdrawAccountSvc;

    /**
     * @mbg.generated 自动生成，如需修改，请删除本行
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public int add(final AfcWithdrawAccountBindFlowMo mo) {
        _log.info("添加提现账户绑定流程");
        // 如果id为空那么自动生成分布式id
        if (mo.getId() == null || mo.getId() == 0) {
            mo.setId(_idWorker.getId());
        }
        return super.add(mo);
    }

    /**
     * 添加提现账户绑定流程
     * 
     * @param mo
     * @return
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public Ro addEx(final AfcWithdrawAccountBindFlowMo mo) {
        _log.info("添加提现账户绑定流程的请求参数为：{}", mo);
        final Date applyTime = new Date();
        mo.setFlowState((byte) 1);
        mo.setApplyTime(applyTime);
        mo.setModifiedTimestamp(System.currentTimeMillis());
        if (mo.getWithdrawType() == 2) {
            mo.setOpenAccountBank("支付宝网银");
        }
        _log.info("添加提现账户绑定流程的请求参数为：{}", mo);
        final int addResult = afcWithdrawAccountBindFlowSvc.add(mo);
        _log.info("添加提现账户绑定流程的返回值为：{}", addResult);
        final Ro ro = new Ro();
        if (addResult != 1) {
            _log.info("添加提现账户绑定流程出现错误，用户id为：{}", mo.getApplicantId());
            ro.setResult(ResultDic.FAIL);
            ro.setMsg("提交失败");
            return ro;
        }

        _log.info("添加提现账户绑定流程成功，用户id为：{}", mo.getApplicantId());
        ro.setResult(ResultDic.SUCCESS);
        ro.setMsg("提交成功");
        return ro;
    }

    /**
     * record
     * 根据申请人id查询最新一条申请提现账户记录
     * 
     * @param applicantId
     * @return
     */
    @Override
    public AfcWithdrawAccountBindFlowMo getNewOneByApplicantId(final Long applicantId) {
        _log.info("根据申请人id查询最新一条申请提现账户记录的参数为: {}", applicantId);
        return _mapper.selectNewOneByApplicantId(applicantId);
    }

    /**
     * 查询申请提现账号记录
     * 
     * @param mo
     * @return
     */
    @SuppressWarnings("unchecked")
    @Override
    public PageInfo<AfcWithdrawAccountBindFlowRo> listEx(final AfcWithdrawAccountBindFlowMo mo, final int pageNum, final int pageSize, final String orderBy) {
        PageInfo<AfcWithdrawAccountBindFlowRo> doSelectPageInfoEx = new PageInfo<>();
        final List<AfcWithdrawAccountBindFlowRo> listEx = new ArrayList<>();
        final PageInfo<AfcWithdrawAccountBindFlowMo> doSelectPageInfo = PageHelper.startPage(pageNum, pageSize, orderBy).doSelectPageInfo(() -> _mapper.selectSelective(mo));
        for (final AfcWithdrawAccountBindFlowMo afcWithdrawAccountBindFlowMo : doSelectPageInfo.getList()) {
            final AfcWithdrawAccountBindFlowRo withdrawAccountBindFlowRo = dozerMapper.map(afcWithdrawAccountBindFlowMo, AfcWithdrawAccountBindFlowRo.class);
            _log.info("查询申请提现账号记录查询用户信息的参数为：{}", afcWithdrawAccountBindFlowMo.getApplicantId());
            final SucUserMo sucUserMo = sucUserSvc.getById(afcWithdrawAccountBindFlowMo.getApplicantId());
            _log.info("查询申请提现账号记录查询用户信息的返回值为：{}", sucUserMo);
            withdrawAccountBindFlowRo.setApplicantName(sucUserMo.getWxNickname());
            listEx.add(withdrawAccountBindFlowRo);
        }
        doSelectPageInfoEx = dozerMapper.map(doSelectPageInfo, PageInfo.class);
        doSelectPageInfoEx.setList(listEx);
        _log.info("查询申请提现账号记录的返回值为：{}", doSelectPageInfoEx);
        return doSelectPageInfoEx;
    }

    /**
     * 审核通过
     * 
     * @param mo
     * @return
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public Ro review(final AfcWithdrawAccountBindFlowMo mo) {
        _log.info("申请提现账号审核通过的参数为：{}", mo);
        final Ro ro = new Ro();
        _log.info("申请提现账号审核通过查询申请提现账号信息的参数为：{}", mo.getId());
        final AfcWithdrawAccountBindFlowMo withdrawAccountBindFlowMo = _mapper.selectByPrimaryKey(mo.getId());
        _log.info("申请提现账号审核通过查询申请提现账号信息的返回值为：{}", withdrawAccountBindFlowMo);
        if (withdrawAccountBindFlowMo == null) {
            _log.error("申请提现账号信息审核通过查询申请提现账号信息时发现没有该申请信息，申请id为：{}", mo.getId());
            ro.setResult(ResultDic.FAIL);
            ro.setMsg("没有发现该提现信息");
            return ro;
        }
        mo.setFlowState((byte) 2);
        _log.info("申请提现账号审核通过修改申请提现账号信息的参数为：{}", mo);
        // 修改申请提现账号信息
        final int updateByPrimaryKeySelectiveResuly = _mapper.updateByPrimaryKeySelective(mo);
        _log.info("申请提现账号审核通过修改申请提现账号信息的返回值为：{}", updateByPrimaryKeySelectiveResuly);
        if (updateByPrimaryKeySelectiveResuly != 1) {
            _log.error("申请提现账号审核通过修改申请提现账号信息时出错，申请id为：{}", mo.getId());
            ro.setResult(ResultDic.FAIL);
            ro.setMsg("审核出错");
            return ro;
        }
        // 添加提现账号信息
        final AfcWithdrawAccountMo accountMo = new AfcWithdrawAccountMo();
        accountMo.setIsDef(true);
        accountMo.setAccountId(withdrawAccountBindFlowMo.getAccountId());
        accountMo.setWithdrawType(withdrawAccountBindFlowMo.getWithdrawType());
        accountMo.setContactTel(withdrawAccountBindFlowMo.getContactTel());
        accountMo.setBankAccountNo(withdrawAccountBindFlowMo.getBankAccountNo());
        accountMo.setBankAccountName(withdrawAccountBindFlowMo.getBankAccountName());
        accountMo.setOpenAccountBank(withdrawAccountBindFlowMo.getOpenAccountBank());
        accountMo.setOpId(mo.getReviewerId());
        accountMo.setMac("不再获取MAC地址");
        accountMo.setIp(withdrawAccountBindFlowMo.getApplicantIp());
        _log.info("申请提现账号审核通过添加提现账号信息的参数为：{}", accountMo);
        final int addResult = afcWithdrawAccountSvc.add(accountMo);
        _log.info("申请提现账号审核通过添加提现账号信息的返回值为：{}", addResult);
        if (addResult != 1) {
            _log.error("申请提现账号审核通过添加提现账号信息时出现错误，申请id为：{}", mo.getId());
            throw new RuntimeException("添加申请提现账号出错");
        }
        _log.info("申请提现账号审核通过成功，申请id为：{}", mo.getId());
        ro.setResult(ResultDic.SUCCESS);
        ro.setMsg("审核成功");
        return ro;
    }

    /**
     * 拒绝申请
     * 
     * @param mo
     * @return
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public int reject(final AfcWithdrawAccountBindFlowMo mo) {
        _log.info("拒绝申请提现账号通过的请求参数为：{}", mo);
        return _mapper.rejectReview(mo);
    }
}
