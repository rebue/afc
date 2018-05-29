package rebue.afc;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringCloudApplication
@EnableFeignClients(basePackages = { "rebue.wxx.svr.feign", "rebue.suc.svr.feign" })
public class AfcApplication {

    public static void main(String[] args) {
        SpringApplication.run(AfcApplication.class, args);
    }

}
