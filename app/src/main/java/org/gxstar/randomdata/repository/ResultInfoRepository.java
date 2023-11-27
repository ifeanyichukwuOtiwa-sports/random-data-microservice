package org.gxstar.randomdata.repository;

import org.gxstar.randomdata.api.dto.ResultInfoDto;
import org.gxstar.randomdata.models.ResultInfo;

import java.util.List;

public interface ResultInfoRepository {
    ResultInfo save(ResultInfoDto resultInfo);
    ResultInfo getById(Long id);
    List<ResultInfo> getForSeeds(String seeds);
}
