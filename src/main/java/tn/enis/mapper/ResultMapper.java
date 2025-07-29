package tn.enis.mapper;

import tn.enis.dto.ResultDTO;
import tn.enis.models.Result;
import tn.enis.models.Topic;
import tn.enis.dto.TopicDTO;

public class ResultMapper {

    public static ResultDTO toDto(Result result) {
        ResultDTO dto = new ResultDTO();
        dto.setId(result.getId());
        dto.setQuizResult(result.getQuizResult());
        dto.setUserId(result.getUserId());
        return dto;
    }
}
