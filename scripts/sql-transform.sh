#!/bin/bash
#Changes the namnes in the provided SQL file to the names we use in the models.
#Run by changing the permissions (chmod u+x ./sql-transform) and run the script (./sql-transform)
#It will generate a new file named transformed-data.sql. Enter that into mysql by typing 'mysql IV1201DB < transformed-data.sql'
sed -e 's/role_id/role/g' -e 's/\(competence_profile.*\)competence_id/\1competence_competence_id/g' -e 's/\(competence_profile.*\)person_id/\1person/g' <initial-data.sql >transformed-data.sql
