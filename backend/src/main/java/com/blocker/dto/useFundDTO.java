package com.blocker.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class useFundDTO {
	String acceesToken;
	String campaignId;
	List<receipt> list;
}
