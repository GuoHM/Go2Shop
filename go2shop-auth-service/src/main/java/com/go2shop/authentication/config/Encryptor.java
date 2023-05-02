package com.go2shop.authentication.config;

/*
 * =========================================================================
 *  Copyright 2020 IHiS Pte. Ltd. All Rights Reserved
 *
 *  This software is confidential and proprietary to IHiS Pte. Ltd. You shall
 *  use this software only in accordance with the terms of the license
 *  agreement you entered into with NCS.  No aspect or part or all of this
 *  software may be reproduced, modified or disclosed without full and
 *  direct written authorisation from NCS.
 *
 *  NCS SUPPLIES THIS SOFTWARE ON AN AS IS BASIS. NCS MAKES NO
 *  REPRESENTATIONS OR WARRANTIES, EITHER EXPRESSLY OR IMPLIEDLY, ABOUT THE
 *  SUITABILITY OR NON-INFRINGEMENT OF THE SOFTWARE. NCS SHALL NOT BE LIABLE
 *  FOR ANY LOSSES OR DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING,
 *  MODIFYING OR DISTRIBUTING THIS SOFTWARE OR ITS DERIVATIVES.
 *  =========================================================================
 */
import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class Encryptor {
	/**
	 * password
	 */
	private static final String FACTOR_PASS = "R(G^YxGf*P:&u,76nh19$";
	@Value("${security.jasypt.algorithm}")
	private String algorithm;
	@Value("${security.jasypt.salt}")
	private String salt;
	@Value("${security.jasypt.iv-generator-classname}")
	private String ivGeneratorClassname;
	@Value("${security.jasypt.salt-generator-classname}")
	private String saltGeneratorClassname;
	@Value("${security.jasypt.string-output-type}")
	private String stringOutputType;

	/**
	 * Configure file encryption read key method
	 */
	@Bean("jasyptStringEncryptor")
	@Primary
	public StringEncryptor createPBEDefault() {
		final PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
		final SimpleStringPBEConfig config = new SimpleStringPBEConfig();
		config.setPassword(FACTOR_PASS + this.salt);
		config.setAlgorithm(this.algorithm);
		config.setKeyObtentionIterations("1000");
		config.setPoolSize("1");
		config.setProviderName("SunJCE");
		config.setSaltGeneratorClassName(this.saltGeneratorClassname);
		config.setIvGeneratorClassName(this.ivGeneratorClassname);
		config.setStringOutputType(this.stringOutputType);
		encryptor.setConfig(config);
		return encryptor;
	}

}
