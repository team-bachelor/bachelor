package cn.org.bachelor.common.auth.service;

import cn.org.bachelor.common.auth.vo.DataPermVo;
import cn.org.bachelor.common.auth.vo.UserSysParam;
import cn.org.bachelor.common.auth.vo.UserVo;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @描述:
 * @创建人: liuzhuo
 * @创建时间: 2019/3/28
 */
public class UserSysServiceTest {
    private MockAuthValueHolderService valueHolder = new MockAuthValueHolderService();

    private UserSysService userSysService;

    @BeforeClass
    public static void prepare() {

    }

    @Before
    public void setUP() {
        UserVo user = new UserVo();
        //csuser.setId("99cf4d4160ab41088cea773fd9839675");
        user.setId("d75fb9754ecd48f79845873840faa0d0");
        user.setAccessToken(
                //cs"jqdUSI0Y7Ty6eNwWIpsbyDggkyVzDjCO37jngH2BZzlCHmBqqzuSaDssoA0U8EK7ekpd2B3H8K2BpZm5pzG1L6Sd2BndGl9qdoWSeiKGjnsBIOn1WqD99AYvJAbzn57eviXI1nesP3aBkNdt2BdLzdmplhYLZFeX9p9l2BxB/SP/713k8="
                "b6Vun3JYuUjZXMxSl1SpSA8aKRBcRT1D3hbr1ToWWNhswljgy2AolOFqfsVB2B/BvDgcaPZM2Be9qiPldqofLXMhqkFCB2BFqLTMMBVUArf4zdw8FXROLookwgRxCiM4ptkkXSLYSZEoFZVr/vyf/Xs1apL91j94h35V3yYX54aPgk="
                //"jqdUSI0Y7Ty6eNwWIpsbyDggkyVzDjCO37jngH2BZzlCHmBqqzuSaDssoA0U8EK7ekpd2B3H8K2BpZm5pzG1L6Sd2BndGl9qdoWSeiKGjnsBIOn1WqD99AYvJAbzn57eviXI1nesP3aBkNdt2BdLzdmplhYLZFeX9p9l2BxB/SP/713k8="
        );
        valueHolder.setCurrentUser(user);
        userSysService = new UserSysService(valueHolder);
    }

    //    @Test
//    public void getUserDetail(){
//        userSysService.findUserDetail(valueHolder.getCurrentUser().getId());
//    }
//
//    @Test
//    public void getUsers(){
//        UserSysParam param = new UserSysParam();
//        param.setOrgId("bcbc3664b35d473aab01d1876f255311");
//        param.setDeptId("f10570f12fa9463884ac634fd7d91c63");
//        param.setUserName("刘");
//        param.setPageSize("1");
//        param.setPage("2");
//        ObjectMapper objectMapper = new ObjectMapper();
//        try {
//            String s = objectMapper.writeValueAsString(userSysService.findUsers(param)) ;
//            System.out.println(s);
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Test
//    public void getDeptDetail(){
//        userSysService.findDeptDetail("740a06be1ac74273ba09380c52028c1f");
//    }
//    @Test
//    public void getDeptDetail(){
//        //"68a99ef178e34d10a53b66e7a98316c9","07d2a7ded91d466aa7ded2b33c2a197b",true,-1,"07d2a7ded91d466aa7ded2b33c2a197b"
//        List l = userSysService.findDepts(
//                "68a99ef178e34d10a53b66e7a98316c9",
//                "07d2a7ded91d466aa7ded2b33c2a197b",
//                true,
//                0);
//    }
    @Test
    public void getAppUsers() {
        UserSysParam param = new UserSysParam();
        param.setClientId("f10e7cc5133841b8a8ba59b86dd9c4f2");
        param.setDeptName("信息");
        param.setOrgName("威海");
        param.setOrgId("68a99ef178e34d10a53b66e7a98316c9");
        param.setDeptId("6e218c1547b249ceba528a1b9fb04c33");
        List l = userSysService.findUserByClientID(param);
    }

    @Test
    public void getUserRoles() {
        //"68a99ef178e34d10a53b66e7a98316c9","07d2a7ded91d466aa7ded2b33c2a197b",true,-1,"07d2a7ded91d466aa7ded2b33c2a197b"
        List l = userSysService.findUserRolesInClient(
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
        DataPermVo l = userSysService.processDataPerm(
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
        DataPermVo vo = userSysService.processDataPerm("68a99ef178e34d10a53b66e7a98316c9", s, true);
    }
}
