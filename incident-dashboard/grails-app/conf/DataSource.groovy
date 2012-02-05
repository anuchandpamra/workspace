dataSource {
	pooled = true
	driverClassName = "oracle.jdbc.driver.OracleDriver"
	username = "grails"
	password = "Lu!Jul11"
	dialect="org.hibernate.dialect.Oracle9Dialect"
}
hibernate {
    cache.use_second_level_cache = true
    cache.use_query_cache = true
    cache.provider_class = 'net.sf.ehcache.hibernate.EhCacheProvider'
}
// environment specific settings
environments {
    development {
		dataSource {
			dbCreate = "update" // one of 'create', 'create-drop','update'
			url = "jdbc:oracle:thin:@bluesheeva_dbserver:1521:devdb"
		}
    }
    test {
        dataSource {
            dbCreate = "update"
            url = "jdbc:hsqldb:mem:testDb"
        }
    }
    production {
        dataSource {
            dbCreate = "update"
            url = "jdbc:hsqldb:file:prodDb;shutdown=true"
        }
    }
}
