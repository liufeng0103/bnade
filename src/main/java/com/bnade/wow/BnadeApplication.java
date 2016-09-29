package com.bnade.wow;

import org.glassfish.jersey.server.ResourceConfig;

public class BnadeApplication extends ResourceConfig {
	public BnadeApplication() {  
        packages(true, "com.bnade.wow.rs");  
    } 
}
