SELECT 
    CASE WITHDRAW_STATE
        WHEN 1 THEN '申请'
        WHEN 3 THEN '已提现'
    END AS '提现状态',
    APPLY_TIME AS '申请时间',
    REAL_AMOUNT AS '提现金额',
    OPEN_ACCOUNT_BANK AS '银行',
    BANK_ACCOUNT_NAME AS '账户名称',
    BANK_ACCOUNT_NO AS '银行账户'
FROM
    AFC_WITHDRAW
WHERE
    ACCOUNT_ID = 204239
ORDER BY APPLY_TIME DESC;

SELECT 
    *
FROM
    afc.AFC_WITHDRAW
WHERE
    ACCOUNT_ID = 204239;