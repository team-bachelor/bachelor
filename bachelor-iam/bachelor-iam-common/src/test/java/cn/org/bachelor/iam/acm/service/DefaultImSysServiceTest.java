package cn.org.bachelor.iam.acm.service;

import cn.org.bachelor.iam.vo.DataPermVo;
import cn.org.bachelor.iam.idm.service.ImSysParam;
import cn.org.bachelor.iam.vo.UserVo;
import cn.org.bachelor.iam.idm.service.ImSysService;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @描述:
 * @创建人: liuzhuo
 * @创建时间: 2019/3/28
 */
public class DefaultImSysServiceTest {
    private MockIamContext iamContext = new MockIamContext();

    @Autowired
    private ImSysService imSysService;

    @BeforeClass
    public static void prepare() {

    }

    @Before
    public void setUp() {
        UserVo user = new UserVo();
        //csuser.setId("99cf4d4160ab41088cea773fd9839675");
        user.setId("d75fb9754ecd48f79845873840faa0d0");
        user.setAccessToken(
                //cs"jqdUSI0Y7Ty6eNwWIpsbyDggkyVzDjCO37jngH2BZzlCHmBqqzuSaDssoA0U8EK7ekpd2B3H8K2BpZm5pzG1L6Sd2BndGl9qdoWSeiKGjnsBIOn1WqD99AYvJAbzn57eviXI1nesP3aBkNdt2BdLzdmplhYLZFeX9p9l2BxB/SP/713k8="
                "b6Vun3JYuUjZXMxSl1SpSA8aKRBcRT1D3hbr1ToWWNhswljgy2AolOFqfsVB2B/BvDgcaPZM2Be9qiPldqofLXMhqkFCB2BFqLTMMBVUArf4zdw8FXROLookwgRxCiM4ptkkXSLYSZEoFZVr/vyf/Xs1apL91j94h35V3yYX54aPgk="
                //"jqdUSI0Y7Ty6eNwWIpsbyDggkyVzDjCO37jngH2BZzlCHmBqqzuSaDssoA0U8EK7ekpd2B3H8K2BpZm5pzG1L6Sd2BndGl9qdoWSeiKGjnsBIOn1WqD99AYvJAbzn57eviXI1nesP3aBkNdt2BdLzdmplhYLZFeX9p9l2BxB/SP/713k8="
        );
        iamContext.setLogonUser(user);
    }
    @Test
    public void getAppUsers() {
        ImSysParam param = new ImSysParam();
        param.setClientId("f10e7cc5133841b8a8ba59b86dd9c4f2");
        param.setDeptName("信息");
        param.setOrgName("威海");
        param.setOrgId("68a99ef178e34d10a53b66e7a98316c9");
        param.setDeptId("6e218c1547b249ceba528a1b9fb04c33");
        List l = imSysService.findUsersByClientID(param);
    }

    @Test
    public void getUserRoles() {
        //"68a99ef178e34d10a53b66e7a98316c9","07d2a7ded91d466aa7ded2b33c2a197b",true,-1,"07d2a7ded91d466aa7ded2b33c2a197b"
        List l = imSysService.findUserRolesInClient(
                "9735062ebec44e05b6c44f97f8b76b46",
                "d75fb9754ecd48f79845873840faa0d0",
                "68a99ef178e34d10a53b66e7a98316c9",
                "b6Vun3JYuUjZXMxSl1SpSA8aKRBcRT1D3hbr1ToWWNhswljgy2AolOFqfsVB2B/BvDgcaPZM2Be9qiPldqofLXMhqkFCB2BFqLTMMBVUArf4zdw8FXROLookwgRxCiM4ptkkXSLYSZEoFZVr/vyf/Xs1apL91j94h35V3yYX54aPgk=");
    }

    @Test
    public void getRoleDepts() {
        //"68a99ef178e34d10a53b66e7a98316c9","07d2a7ded91d466aa7ded2b33c2a197b",true,-1,"07d2a7ded91d466aa7ded2b33c2a197b"
        HashSet<String> depts = new HashSet<>();
        depts.add("294566958f4341148d9cb6ef389314cc");
        DataPermVo l = imSysService.processDataPerm(
                "68a99ef178e34d10a53b66e7a98316c9",
                depts,
                true);
    }

    //
    @Test
    public void getDataprm() {
        Set<String> s = new HashSet<>();
//        s.add("68a99ef178e34d10a53b66e7a98316c9");
//        s.add("3523a03fe73446f68edd1293de6affa1");
//        s.add("64c549ed272a4e759505dfaf9dc4e4ec");
        s.add("07d2a7ded91d466aa7ded2b33c2a197b");
//        s.add("b630d2805553433ea33e38b7b706564e");
        DataPermVo vo = imSysService.processDataPerm("68a99ef178e34d10a53b66e7a98316c9", s, true);
    }
}
