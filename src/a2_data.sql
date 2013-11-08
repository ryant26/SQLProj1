--Fill Patient
insert into PATIENT values(1, 'John', '10 Wallaby way', '01-JAN-1993', '888-9900');
insert into PATIENT values(2, 'Jason', '11 apple road', '17-JUN-1993', '777-2141');
insert into PATIENT values(3, 'Jane', '122 orange road', '12-APR-1993', '234-754');
insert into PATIENT values(4, 'Sarah', '99-11 St.', '03-FEB-1989', '840-1046');
insert into PATIENT values(5, 'Alex', '987 Gator St.', '30-NOV-1967', '0987-3344');
insert into PATIENT values(6, 'Ryan', '4422 Park Place', '25-AUG-1977', '476-9109');
insert into PATIENT values(7, 'Lemma', '1234 boat rd.', '17-SEP-1980', '487-1976');
insert into PATIENT values(8, 'George', '76 Chair Ave', '05-MAR-1982', '555-0952');
insert into PATIENT values(9, 'Goldie', '77 Oldtimers Ave', '05-MAR-1940', '555-0952');


--Fill Doctor
insert into doctor values (1, '10 Citadel Way', '556-2892', '272-9999', 4);
insert into doctor values (2, '12 Gable Way', '576-1234', '111-2222', 5);
insert into doctor values (3, '12 Hiplace Ave', '576-1234', '111-2222', 6);
insert into doctor values (4, '12 Simpleton Cir', '576-1234', '111-2222', 7);

--Fill medical Lab
insert into medical_lab values('X-Ray Laboritories', '87 Robitussin Ave', '235-6865');
insert into medical_lab values('House of Surgeons', '278 Cutting Place', '590-8960');
insert into medical_lab values('Prarie Med Lab', '87 Buckles Ave, Edmonton', '987-6543');
insert into medical_lab values('Oilers Lab', '278 Champion Place, Edmonton', '760-0012');

--Fill Test Type
insert into test_type values(1,'X ray', 'X ray Req', '**THIS IS THE TESTING PROCEDURE FOR X-Rays');
insert into test_type values(2,'CT Scan', 'CT Scan Req', '**THIS IS THE TESTING PROCEDURE FOR CT SCAN');
insert into test_type values(3,'Bone Marrow Check', 'Bone Marrow Req', '**THIS IS THE TESTING PROCEDURE FOR BONE MARROW TEST');



--Fill can conduct
insert into can_conduct values('X-Ray Laboritories', 1);
insert into can_conduct values('X-Ray Laboritories', 2);
insert into can_conduct values('House of Surgeons', 3);
insert into can_conduct values('House of Surgeons', 2);
insert into can_conduct values('Prarie Med Lab', 1);
insert into can_conduct values('Prarie Med Lab', 2);
insert into can_conduct values('Prarie Med Lab', 3);
insert into can_conduct values('Oilers Lab', 3);
insert into can_conduct values('Oilers Lab', 1);


--fill not_allowed
insert into not_allowed values(1,1);
insert into not_allowed values(1,2);
insert into not_allowed values(2,3);
insert into not_allowed values(4,2);
insert into not_allowed values(5,1);
insert into not_allowed values(8,3);

--fill test record
insert into test_record values(1,3,1,1,'House of Surgeons', 'normal', to_date('03-JUN-2003','dd-mon-yyyy'), to_date('05-JUN-2003','dd-mon-yyyy'));
insert into test_record values(2,1,2,2,'House of Surgeons', 'Abnormal', to_date('03-JUN-2003','dd-mon-yyyy'), to_date('05-JUN-2003','dd-mon-yyyy'));
insert into test_record values(3,2,2,2,'X-Ray Laboritories', 'normal', to_date('03-JUN-2003','dd-mon-yyyy'), to_date('05-JUN-2003','dd-mon-yyyy'));
insert into test_record values(4,3,3,1,'House of Surgeons', 'Abnormal',to_date('05-JUL-2010','dd-mon-yyyy'), to_date('10-JUL-2010','dd-mon-yyyy'));
insert into test_record values(5,1,4,2,'X-Ray Laboritories', 'normal',to_date('25-SEP-2012','dd-mon-yyyy'), to_date('15-SEP-2012','dd-mon-yyyy'));
insert into test_record values(6,2,5,2,'Prarie Med Lab', 'Abnormal',to_date('06-OCT-2013','dd-mon-yyyy'), to_date('12-OCT-2013','dd-mon-yyyy'));
insert into test_record values(7,3,6,2,'Oilers Lab', 'normal',to_date('12-APR-2013','dd-mon-yyyy'), to_date('15-APR-2013','dd-mon-yyyy'));
insert into test_record values(8,2,6,2,'Prarie Med Lab', 'normal',to_date('06-OCT-2004', 'dd-mon-yyyy'), to_date('12-OCT-2003', 'dd-mon-yyyy'));
insert into test_record values(9,2,7,1,'Prarie Med Lab', 'normal',to_date('06-OCT-2004', 'dd-mon-yyyy'), to_date('12-OCT-2012', 'dd-mon-yyyy'));
insert into test_record values(10,2,7,1,'Prarie Med Lab', 'normal',to_date('06-OCT-2004', 'dd-mon-yyyy'), to_date('12-OCT-2010', 'dd-mon-yyyy'));
insert into test_record values(11,2,7,4,'Prarie Med Lab', 'normal',to_date('06-OCT-2004', 'dd-mon-yyyy'), to_date('12-OCT-2013', 'dd-mon-yyyy'));
insert into test_record values(12,2,7,2,'Prarie Med Lab', 'normal',to_date('06-OCT-2004', 'dd-mon-yyyy'), to_date('12-OCT-2011', 'dd-mon-yyyy'));
insert into test_record values(13,2,3,3,'Prarie Med Lab', 'normal',to_date('06-OCT-2004', 'dd-mon-yyyy'), to_date('12-OCT-2003', 'dd-mon-yyyy'));
insert into test_record values(14,1,4,4,'X-Ray Laboritories', 'Abnormal',to_date('15-AUG-2003', 'dd-mon-yyyy'), to_date('30-AUG-2003', 'dd-mon-yyyy'));
insert into test_record values(15,1,8,4,'X-Ray Laboritories', 'Abnormal',to_date('15-AUG-2003', 'dd-mon-yyyy'), to_date('30-AUG-2003', 'dd-mon-yyyy'));
insert into test_record values(16,3,4,3,'X-Ray Laboritories', 'Abnormal',to_date('15-AUG-2003', 'dd-mon-yyyy'), to_date('30-AUG-2003', 'dd-mon-yyyy'));
insert into test_record values(17,3,4,3,'X-Ray Laboritories', 'Abnormal',to_date('15-AUG-2013', 'dd-mon-yyyy'), to_date('30-AUG-2013', 'dd-mon-yyyy'));
insert into test_record values(18,2,4,4,'X-Ray Laboritories', 'Abnormal',to_date('15-AUG-2013', 'dd-mon-yyyy'), to_date('30-AUG-2013', 'dd-mon-yyyy'));
insert into test_record values(19,3,4,4,'X-Ray Laboritories', 'Abnormal',to_date('15-AUG-2013', 'dd-mon-yyyy'), to_date('30-AUG-2013', 'dd-mon-yyyy'));
