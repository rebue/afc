package rebue.afc.gen;

import java.io.IOException;
import java.sql.SQLException;

import org.mybatis.generator.exception.InvalidConfigurationException;
import org.mybatis.generator.exception.XMLParserException;

import rebue.mbgx.MybatisGeneratorWrap;

/**
 * 自动生成Mybatis
 */
public class AfcGen {

    public static void main(String[] args) throws IOException, XMLParserException, InvalidConfigurationException, SQLException, InterruptedException {
        MybatisGeneratorWrap.gen(true, "conf/mbg-afc.properties");
    }
}
