--                                                                              
-- DROP TABLES --                                                               
--                                                                              
DROP TABLE AIRLINE CASCADE CONSTRAINTS;                                         
DROP TABLE AIRPORT CASCADE CONSTRAINTS;                                         
DROP TABLE BIN$mFpCemJquJTgQAB/AQATUA==$0 CASCADE CONSTRAINTS;                  
DROP TABLE BIN$mFpCemJuuJTgQAB/AQATUA==$0 CASCADE CONSTRAINTS;                  
DROP TABLE BIN$mFpCemJyuJTgQAB/AQATUA==$0 CASCADE CONSTRAINTS;                  
DROP TABLE FLIGHT CASCADE CONSTRAINTS;                                          
DROP TABLE INCIDENT CASCADE CONSTRAINTS;                                        
DROP TABLE LOCATION CASCADE CONSTRAINTS;                                        
DROP TABLE LOCATION_INCIDENT CASCADE CONSTRAINTS;                               
DROP TABLE MAP CASCADE CONSTRAINTS;                                             
DROP TABLE TRIP CASCADE CONSTRAINTS;                                            
--                                                                              
-- CREATE TABLES --                                                             
--                                                                              
CREATE TABLE AIRLINE (                                                          
ID                              NUMBER(19)          NOT NULL,                   
VERSION                         NUMBER(19)          NOT NULL,                   
FREQUENT_FLYER                  VARCHAR2(255)       NOT NULL,                   
IATA                            VARCHAR2(255)       NOT NULL,                   
NAME                            VARCHAR2(100)       NOT NULL                    
);                                                                              
CREATE TABLE AIRPORT (                                                          
ID                              NUMBER(19)          NOT NULL,                   
VERSION                         NUMBER(19)          NOT NULL,                   
CITY                            VARCHAR2(255)       NOT NULL,                   
COUNTRY                         VARCHAR2(255)       NOT NULL,                   
IATA                            VARCHAR2(255)       NOT NULL,                   
NAME                            VARCHAR2(255)       NOT NULL,                   
STATE                           VARCHAR2(255)       NULL,                       
LAT                             VARCHAR2(255)       NULL,                       
LNG                             VARCHAR2(255)       NULL                        
);                                                                              
CREATE TABLE FLIGHT (                                                           
ID                              NUMBER(19)          NOT NULL,                   
VERSION                         NUMBER(19)          NOT NULL,                   
AIRLINE_ID                      NUMBER(19)          NOT NULL,                   
ARRIVAL_AIRPORT_ID              NUMBER(19)          NOT NULL,                   
ARRIVAL_DATE                    TIMESTAMP(6)        NOT NULL,                   
DEPARTURE_AIRPORT_ID            NUMBER(19)          NOT NULL,                   
DEPARTURE_DATE                  TIMESTAMP(6)        NOT NULL,                   
FLIGHT_NUMBER                   VARCHAR2(255)       NOT NULL,                   
TRIP_ID                         NUMBER(19)          NOT NULL                    
);                                                                              
CREATE TABLE INCIDENT (                                                         
ID                              NUMBER(19)          NOT NULL,                   
VERSION                         NUMBER(19)          NOT NULL,                   
LOCATION_ID                     NUMBER(19)          NULL,                       
TIME_OF                         TIMESTAMP(6)        NULL,                       
DESCRIPTION                     VARCHAR2(1000)      NULL                        
);                                                                              
CREATE TABLE LOCATION (                                                         
ID                              NUMBER(19)          NOT NULL,                   
VERSION                         NUMBER(19)          NOT NULL,                   
BUILDING_NUMBER                 VARCHAR2(255)       NULL,                       
CITY                            VARCHAR2(100)       NULL,                       
COUNTRY                         VARCHAR2(255)       NULL,                       
LAT                             VARCHAR2(255)       NULL,                       
LNG                             VARCHAR2(255)       NULL,                       
STATE                           VARCHAR2(100)       NULL,                       
STREET1                         VARCHAR2(255)       NULL,                       
STREET2                         VARCHAR2(255)       NULL,                       
FORMATTED_ADDRESS               VARCHAR2(255)       NULL                        
);                                                                              
CREATE TABLE LOCATION_INCIDENT (                                                
LOCATION_INCIDENTS_ID           NUMBER(19)          NULL,                       
INCIDENT_ID                     NUMBER(19)          NULL                        
);                                                                              
CREATE TABLE MAP (                                                              
ID                              NUMBER(19)          NOT NULL,                   
VERSION                         NUMBER(19)          NOT NULL                    
);                                                                              
CREATE TABLE TRIP (                                                             
ID                              NUMBER(19)          NOT NULL,                   
VERSION                         NUMBER(19)          NOT NULL,                   
NAME                            VARCHAR2(255)       NOT NULL                    
);                                                                              
--                                                                              
-- PRIMARY KEYS --                                                              
--                                                                              
ALTER TABLE AIRLINE ADD (                                                       
CONSTRAINT SYS_C0011418                                                         
PRIMARY KEY (                                                                   
ID                                                                              
)                                                                               
USING INDEX );                                                                  
ALTER TABLE AIRPORT ADD (                                                       
CONSTRAINT SYS_C0011426                                                         
PRIMARY KEY (                                                                   
ID                                                                              
)                                                                               
USING INDEX );                                                                  
ALTER TABLE BIN$mFpCemJuuJTgQAB/AQATUA==$0 ADD (                                
CONSTRAINT BIN$mFpCemJsuJTgQAB/AQATUA==$0                                       
PRIMARY KEY (                                                                   
NAME                                                                            
)                                                                               
USING INDEX );                                                                  
ALTER TABLE BIN$mFpCemJyuJTgQAB/AQATUA==$0 ADD (                                
CONSTRAINT BIN$mFpCemJwuJTgQAB/AQATUA==$0                                       
PRIMARY KEY (                                                                   
ID                                                                              
)                                                                               
USING INDEX );                                                                  
ALTER TABLE FLIGHT ADD (                                                        
CONSTRAINT SYS_C0011436                                                         
PRIMARY KEY (                                                                   
ID                                                                              
)                                                                               
USING INDEX );                                                                  
ALTER TABLE INCIDENT ADD (                                                      
CONSTRAINT SYS_C0011479                                                         
PRIMARY KEY (                                                                   
ID                                                                              
)                                                                               
USING INDEX );                                                                  
ALTER TABLE LOCATION ADD (                                                      
CONSTRAINT SYS_C0011491                                                         
PRIMARY KEY (                                                                   
ID                                                                              
)                                                                               
USING INDEX );                                                                  
ALTER TABLE MAP ADD (                                                           
CONSTRAINT SYS_C0011496                                                         
PRIMARY KEY (                                                                   
ID                                                                              
)                                                                               
USING INDEX );                                                                  
ALTER TABLE TRIP ADD (                                                          
CONSTRAINT SYS_C0011440                                                         
PRIMARY KEY (                                                                   
ID                                                                              
)                                                                               
USING INDEX );                                                                  
--                                                                              
-- FOREIGN KEYS --                                                              
--                                                                              
ALTER TABLE FLIGHT ADD (                                                        
CONSTRAINT FKB4318470261B20F5                                                   
FOREIGN KEY (                                                                   
AIRLINE_ID                                                                      
) REFERENCES AIRLINE (                                                          
ID                                                                              
) );                                                                            
ALTER TABLE FLIGHT ADD (                                                        
CONSTRAINT FKB431847018DE2600                                                   
FOREIGN KEY (                                                                   
DEPARTURE_AIRPORT_ID                                                            
) REFERENCES AIRPORT (                                                          
ID                                                                              
) );                                                                            
ALTER TABLE FLIGHT ADD (                                                        
CONSTRAINT FKB4318470B4C1A5DB                                                   
FOREIGN KEY (                                                                   
ARRIVAL_AIRPORT_ID                                                              
) REFERENCES AIRPORT (                                                          
ID                                                                              
) );                                                                            
ALTER TABLE FLIGHT ADD (                                                        
CONSTRAINT FKB43184702953D7F                                                    
FOREIGN KEY (                                                                   
TRIP_ID                                                                         
) REFERENCES TRIP (                                                             
ID                                                                              
) );                                                                            
ALTER TABLE INCIDENT ADD (                                                      
CONSTRAINT FK_12345                                                             
FOREIGN KEY (                                                                   
LOCATION_ID                                                                     
) REFERENCES LOCATION (                                                         
ID                                                                              
) );                                                                            
ALTER TABLE LOCATION_INCIDENT ADD (                                             
CONSTRAINT FKF754B21CE5642150                                                   
FOREIGN KEY (                                                                   
INCIDENT_ID                                                                     
) REFERENCES INCIDENT (                                                         
ID                                                                              
) );                                                                            
ALTER TABLE LOCATION_INCIDENT ADD (                                             
CONSTRAINT FKF754B21CF535550E                                                   
FOREIGN KEY (                                                                   
LOCATION_INCIDENTS_ID                                                           
) REFERENCES LOCATION (                                                         
ID                                                                              
) );                                                                            
--                                                                              
-- DROP SEQUENCES --                                                            
--                                                                              
--                                                                              
-- CREATE SEQUENCES --                                                          
--                                                                              
--                                                                              
-- INDEXES --                                                                   
--                                                                              
