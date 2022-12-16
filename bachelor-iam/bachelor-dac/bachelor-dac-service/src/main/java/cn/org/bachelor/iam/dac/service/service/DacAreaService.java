package cn.org.bachelor.iam.dac.service.service;

import cn.org.bachelor.iam.dac.service.dao.DacAreaMapper;
import cn.org.bachelor.iam.dac.service.domain.DacArea;
import cn.org.bachelor.iam.dac.service.pojo.vo.DacAreaVo;
import cn.org.bachelor.iam.dac.service.pojo.vo.SearchDacAreaVo;
import cn.org.bachelor.iam.dac.service.pojo.vo.UpdateDacAreaVo;
import cn.org.bachelor.web.util.UuidUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author Hyw
 * @PackageName eprs
 * @Package cn.org.bachelor.iam.acm.service
 * @Date 2022/11/28 15:50
 * @Version 1.0
 */
@Service
public class DacAreaService {

    @Resource
    private DacAreaMapper dacAreaMapper;

    private final String operator = "test";

    /**
     * @describe: 如果是顶级的 X+000000
     * @param: * @param null
     * @return:
     * @date 2022/11/28 13:37
     * @author: Hyw
     */
    private String getCode(String code, String level) {
        if ("0".equals(level)) { //顶级
            List<Integer> list = new ArrayList<>();
            //查询所有的 area 中parentCode为空的值
            List<String> dacAreas = dacAreaMapper.selectParentCodeIsNull();
            dacAreas.stream().forEach(item -> {
                list.add(Integer.parseInt(item.substring(0, 1)));
            });
            System.out.println(list);
            Optional<Integer> integer = list.stream().reduce(Integer::max);
            Integer i = Integer.parseInt("0");
            if (integer != null && integer.isPresent()) {
                i = integer.get();
            }
            return (i + 1) + "000000";
        } else if ("1".equals(level)) { //同级
            //如果倒数两位数是00代表就是县区同级，如果不是 就是镇街的同级
            List<Integer> sameLevel = new ArrayList<>();
            //根据code查询出parentCode 然后查询出所有的 code
            List<String> sameLevelAreas = dacAreaMapper.selectAllCode(code);
            if ("00".equals(code.substring(5, 7))) {
                if (!sameLevelAreas.stream().filter(item -> !item.substring(3, 4).equals("0")).collect(Collectors.toList()).isEmpty()) {
                    sameLevelAreas.stream().forEach(item -> {
                        sameLevel.add(Integer.parseInt(item.substring(3, 5)));
                    });
                    Optional<Integer> integer = sameLevel.stream().reduce(Integer::max);
                    Integer i = Integer.parseInt("0");
                    if (integer != null && integer.isPresent()) {
                        i = integer.get();
                    }
                    return code.substring(0, 3) + (i + 1) + code.substring(5, 7);
                } else {
                    sameLevelAreas.stream().forEach(item -> {
                        sameLevel.add(Integer.parseInt(item.substring(4, 5)));
                    });
                    Optional<Integer> integer = sameLevel.stream().reduce(Integer::max);
                    Integer i = Integer.parseInt("0");
                    if (integer != null && integer.isPresent()) {
                        i = integer.get();
                    }
                    if (i.equals(9)) {
                        return code.substring(0, 3) + (i + 1) + code.substring(5, 7);
                    }
                    return code.substring(0, 4) + (i + 1) + code.substring(5, 7);
                }
            } else {
                if (!sameLevelAreas.stream().filter(item -> !item.substring(5, 6).equals("0")).collect(Collectors.toList()).isEmpty()) {
                    sameLevelAreas.stream().forEach(item -> {
                        sameLevel.add(Integer.parseInt(item.substring(5, 7)));
                    });
                    Optional<Integer> integer = sameLevel.stream().reduce(Integer::max);
                    Integer i = Integer.parseInt("0");
                    if (integer != null && integer.isPresent()) {
                        i = integer.get();
                    }
                    return code.substring(0, 5) + (i + 1);
                } else {
                    sameLevelAreas.stream().forEach(item -> {
                        sameLevel.add(Integer.parseInt(item.substring(6, 7)));
                    });
                    Optional<Integer> integer = sameLevel.stream().reduce(Integer::max);
                    Integer i = Integer.parseInt("0");
                    if (integer != null && integer.isPresent()) {
                        i = integer.get();
                    }
                    if (integer.get().equals(9)) {
                        return code.substring(0, 5) + (i + 1);
                    }
                    return code.substring(0, 6) + (i + 1);
                }
            }
        } else if ("2".equals(level)) {//下级
            List<Integer> lowerLevel = new ArrayList<>();
            List<String> lowerLevelAreas = dacAreaMapper.selectCodeByParentCode(code);
            if (code.startsWith("000000", 1)) {
                if (!lowerLevelAreas.stream().filter(lowerLevelItem -> !lowerLevelItem.substring(3, 4).equals("0")).collect(Collectors.toList()).isEmpty()) {
                    lowerLevelAreas.stream().forEach(item -> {
                        lowerLevel.add(Integer.parseInt(item.substring(3, 5)));
                    });
                    Optional<Integer> integer = lowerLevel.stream().reduce(Integer::max);
                    Integer i = Integer.parseInt("0");
                    if (integer != null && integer.isPresent()) {
                        i = integer.get();
                    }
                    return code.substring(0, 3) + (i + 1) + code.substring(5, 7);
                } else {
                    lowerLevelAreas.stream().forEach(item -> {
                        System.err.println(item);
                        lowerLevel.add(Integer.parseInt(item.substring(4, 5)));
                    });

                    Optional<Integer> integer = lowerLevel.stream().reduce(Integer::max);
                    Integer i = Integer.parseInt("0");
                    if (integer != null && integer.isPresent()) {
                        i = integer.get();
                    }
                    if (i.equals(9)) {
                        return code.substring(0, 3) + (i + 1) + code.substring(5, 7);
                    }
                    return code.substring(0, 4) + (i + 1) + code.substring(5, 7);
                }
            } else {
                if (!lowerLevelAreas.stream().filter(item -> !item.substring(5, 6).equals("0")).collect(Collectors.toList()).isEmpty()) {
                    lowerLevelAreas.stream().forEach(item -> {
                        lowerLevel.add(Integer.parseInt(item.substring(5, 7)));
                    });
                    Optional<Integer> integer = lowerLevel.stream().reduce(Integer::max);
                    Integer i = Integer.parseInt("0");
                    if (integer != null && integer.isPresent()) {
                        i = integer.get();
                    }
                    return code.substring(0, 5) + i + 1;
                } else {
                    lowerLevelAreas.stream().forEach(item -> {
                        lowerLevel.add(Integer.parseInt(item.substring(6, 7)));
                    });
                    Optional<Integer> integer = lowerLevel.stream().reduce(Integer::max);
                    Integer i = Integer.parseInt("0");
                    if (integer != null && integer.isPresent()) {
                        i = integer.get();
                    }
                    if (i.equals(9)) {
                        return code.substring(0, 5) + (i + 1);
                    }
                    return code.substring(0, 6) + (i + 1);
                }
            }
        }
        return Strings.EMPTY;
    }

    /**
     * @describe: 当前选中的code
     * @param: * @param String code, String level
     * @return:
     * @date 2022/11/28 15:54
     * @author: Hyw
     */

    public void addArea(DacAreaVo dacAreaVo) {
        DacArea dacArea = new DacArea();
        Date addSystemTime = new Date();
        dacArea.setId(UuidUtil.getUUID());
        dacArea.setName(dacAreaVo.getName());
        dacArea.setCode(getCode(dacAreaVo.getCode(), dacAreaVo.getLevel()));
        if (dacAreaVo.getLevel().equals("1")) {
            dacArea.setParentCode(dacAreaVo.getParentCode());
        } else if (dacAreaVo.getLevel().equals("2")) {
            dacArea.setParentCode(dacAreaVo.getCode());
        }
        dacArea.setCreateTime(addSystemTime);
        dacArea.setUpdateTime(addSystemTime);
        dacArea.setCreateUser(operator);
        dacArea.setUpdateUser(operator);
        dacAreaMapper.insertDacArea(dacArea);
    }

    public List<Map<Object, Object>> areaTree(String deep) {
        List<Map<Object, Object>> mapList = new ArrayList<>();
        List<DacArea> dacAreas = dacAreaMapper.selectDacAreaParentCodeIsNull();
        if("1".equals(deep)) { //镇街级
            dacAreas.stream().forEach(item -> {
                Map<Object, Object> mapOne = new HashMap<>();
                List<DacArea> levelTwoList = dacAreaMapper.selectDacAreaByParentCode(item.getCode());
                levelTwoList.stream().forEach(itemThree -> {
                    List<DacArea> levelThreeList = dacAreaMapper.selectDacAreaByParentCode(itemThree.getCode());
                    levelThreeList.stream().forEach(s -> {
                        mapOne.put("id", s.getId());
                        mapOne.put("name", s.getName());
                        mapOne.put("code", s.getCode());
                        mapOne.put("parentCode", s.getParentCode());
                        mapList.add(mapOne);
                    });

                });
            });
            return mapList;
        } else if ("2".equals(deep)) {
            dacAreas.stream().forEach(item -> {
                Map<Object, Object> mapOne = new HashMap<>();
                List<DacArea> levelTwoList = dacAreaMapper.selectDacAreaByParentCode(item.getCode());
                levelTwoList.stream().forEach(itemThree -> {
                    mapOne.put("id", itemThree.getId());
                    mapOne.put("name", itemThree.getName());
                    mapOne.put("code", itemThree.getCode());
                    mapOne.put("parentCode", itemThree.getParentCode());
                    List<DacArea> levelThreeList = dacAreaMapper.selectDacAreaByParentCode(itemThree.getCode());
                    mapOne.put("children", levelThreeList);
                    mapList.add(mapOne);
                });
            });
            return mapList;
        } else if ("3".equals(deep)) {
            dacAreas.stream().forEach(item -> {
                Map<Object, Object> mapOne = new HashMap<>();
                mapOne.put("name", item.getName());
                mapOne.put("code", item.getCode());
                mapOne.put("id", item.getId());
                List<DacArea> levelTwoList = dacAreaMapper.selectDacAreaByParentCode(item.getCode());
                levelTwoList.stream().forEach(itemThree -> {
                    List<DacArea> levelThreeList = dacAreaMapper.selectDacAreaByParentCode(itemThree.getCode());
                    itemThree.setChildren(levelThreeList);
                });
                mapOne.put("children", levelTwoList);
                mapList.add(mapOne);
            });
            return mapList;
        }
        return mapList;
    }

    public PageInfo<DacArea> getDacAreaList(SearchDacAreaVo searchDacAreaVo) {
        PageHelper.startPage(searchDacAreaVo.getPageNum(), searchDacAreaVo.getPageSize()); //这句要在查询代码之前，pageNum从1开始
        List<DacArea> list = dacAreaMapper.getDacAreaList(searchDacAreaVo);
        return new PageInfo<DacArea>(list);
    }

    public void deleteArea(String id) {
        dacAreaMapper.deleteArea(id);
    }

    public void updateArea(UpdateDacAreaVo updateDacAreaVo) {
        dacAreaMapper.updateDacArea(updateDacAreaVo.getId(), updateDacAreaVo.getName());
    }

    public List<Map<Object, Object>> getAllArea() {
        return dacAreaMapper.getAllArea().stream().map(item -> {
            if (item.get("parentTypeCode") == null) {
                item.put("parentTypeCode", null);
            }
            item.put("dataType", "district_area");
            return item;
        }).collect(Collectors.toList());
    }

    public String getCodeByName(String name) {
        StringBuffer buffer = new StringBuffer();
        String code = dacAreaMapper.selectCodeByName(name);//当前名字对应的编码
        String code1 = dacAreaMapper.selectParentCodeByCode(code);//父编码
        String code2 = dacAreaMapper.selectParentCodeByCode(code1);
        if (StringUtils.isNotEmpty(code) && StringUtils.isNotEmpty(code1) && StringUtils.isNotEmpty(code2)) {
            buffer.append(code2);
            buffer.append(",");
            buffer.append(code1);
            buffer.append(",");
            buffer.append(code);
            return buffer.toString();
        } else if (StringUtils.isNotEmpty(code) && StringUtils.isNotEmpty(code1)) {
            return buffer.append(code1 + "," + code).toString();
        } else {
            return buffer.append(code).toString();
        }

    }

    public String getNameByCode(String code) {
        StringBuffer buffer = new StringBuffer();
        Arrays.asList(code.split(",")).stream().forEach(item -> {
            buffer.append(dacAreaMapper.selectNameByCode(item) + ",");
        });
        return buffer.toString().substring(0, buffer.length() - 1);
    }

    public Map<String, String> getAllAreaCode() {
        Map<String, String> map = new HashMap<>();
        List<Map<String, String>> list = dacAreaMapper.selectAllAreaCode();
        for (Map<String, String> obj : list) {
            if (StringUtils.isNotBlank(obj.get("code")) && StringUtils.isNotBlank(obj.get("name"))) {
                map.put(obj.get("code"), obj.get("name"));
            }
        }
        return map;
    }
}
