
insert into PATIENT values(1, 'John', '10 Wallaby way', '01-JAN-1993', '888-9900');
insert into PATIENT values(2, 'Jason', '11 apple road', '17-JUN-1993', '777-2141');
insert into PATIENT values(3, 'Jane', '122 orange road', '12-APR-1993', '234-754');
insert into PATIENT values(4, 'Sarah', '99-11 St.', '03-FEB-1989', '840-1046');
insert into PATIENT values(5, 'Alex', '987 Gator St.', '30-NOV-1967', '0987-3344');
insert into PATIENT values(6, 'Ryan', '4422 Park Place', '25-AUG-1977', '476-9109');


insert into doctor values (1, '10 Citadel Way', '556-2892', '272-9999', 4);
insert into doctor values (2, '12 Gable Way', '576-1234', '111-2222', 5);


insert into medical_lab values('X-Ray Laboritories', '87 Robitussin Ave', '235-6865');
insert into medical_lab values('House of Surgeons', '278 Cutting Place', '590-8960');
insert into medical_lab values('Prarie Med Lab', '87 Buckles Ave, Edmonton', '987-6543');
insert into medical_lab values('Oilers Lab', '278 Champion Place, Edmonton', '760-0012');


insert into test_type values(1,'Bronchitis Test', 'Sore Throat', '**THIS IS THE TESTING PROCEDURE FOR BRONCHITIS');
insert into test_type values(2,'CT Scan', 'CT Scan Req', '**THIS IS THE TESTING PROCEDURE FOR CT SCAN');
insert into test_type values(3,'Bone Marrow Check', 'Bone Marrow Req', '**THIS IS THE TESTING PROCEDURE FOR BONE MARROW TEST');




insert into can_conduct values('X-Ray Laboritories', 1);
insert into can_conduct values('X-Ray Laboritories', 2);
insert into can_conduct values('House of Surgeons', 3);
insert into can_conduct values('House of Surgeons', 2);
insert into can_conduct values('Prarie Med Lab', 1);
insert into  can_conduct values('Prarie Med Lab', 2);
insert into can_conduct values('Prarie Med Lab', 3);
insert into can_conduct values('Oilers Lab', 3);
insert into can_conduct values('Oilers Lab', 1);



insert into not_allowed values(1,1);
insert into not_allowed values(1,2);
insert into not_allowed values(2,3);
insert into not_allowed values(4,2);
insert into not_allowed values(5,1);



insert into test_record values(1,3,1,1,'House of Surgeons', 'Positive','03-JUN-2012','05-JUN-2012' );
insert into test_record values(2,1,2,2,'House of Surgeons', 'NEGATIVE','04-JUL-2013', '10-JUL-2013');
insert into test_record values(3,2,2,2,'X-Ray Laboritories', 'Positive','12-APR-2004','20-APR-2004');
insert into test_record values(4,3,3,1,'House of Surgeons', 'NEGATIVE','13-May-2011','18-May-2011');
insert into test_record values(5,1,4,2,'X-Ray Laboritories', 'Positive','15-AUG-2012', '30-AUG-2012');
insert into test_record values(6,2,5,2,'Prarie Med Lab', 'NEGATIVE','15-SEP-2004', '25-SEP-2004');
insert into test_record values(7,3,6,2,'Oilers Lab', 'Positive','06-OCT-2009', '12-OCT-2009');
insert into test_record values(8,2,6,2,'Oilers Lab', 'Positive','06-OCT-2004', '12-OCT-2004');