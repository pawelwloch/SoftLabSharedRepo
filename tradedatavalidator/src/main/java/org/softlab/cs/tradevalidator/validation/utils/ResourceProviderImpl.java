/**
 * @author Pawel Wloch @SoftLab
 * @date 14.10.2018
 */
package org.softlab.cs.tradevalidator.validation.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.softlab.cs.tradevalidator.validation.rules.CurrencyData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
@Primary
public class ResourceProviderImpl implements ResourceProvider {

	@Autowired
	private ResourceLoader resourceLoader;

	private String livrRules;
	private List<CurrencyData> currencyDataList;
	private Map<String, List<Date>> currencyInfoMap;
	
	@PostConstruct
	public void init() throws IOException {
		livrRules = readFileIntoString("classpath:LIVR validation rules.txt");

		ObjectMapper objectMapper = new ObjectMapper();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		objectMapper.setDateFormat(df);
		String jsonCurrencyArray = readFileIntoString("classpath:currency_data.json");
		currencyDataList = objectMapper.readValue(jsonCurrencyArray, new TypeReference<List<CurrencyData>>(){});
	}

	@Override
	public String getLIVRRules() {
		return livrRules;
	}

	@Override
	public Map<String, List<Date>> getCurrencyInfoMap() {
		if(currencyInfoMap==null) {
			currencyInfoMap = currencyDataList.stream().collect(
					Collectors.toMap(CurrencyData::getCode, CurrencyData::getHolidays));
		}
		return currencyInfoMap;
	}
	
	private String readFileIntoString(String springFileLocation) throws IOException {
		String fileContents;
		Resource resource = resourceLoader.getResource(springFileLocation);
		InputStream stream = resource.getInputStream();
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(stream))) {
			fileContents = reader.lines().collect(Collectors.joining("\n"));
		}
		return fileContents;
	}

}
