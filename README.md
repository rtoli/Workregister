# Workregister
A program used to keep records of different employees and their working times.

Working time is the time from the start to the end of work, excluding rest breaks. The periods of working time of an individual employee must not overlap and must always be at least 1 minute each.
Working time is recorded and checked in one-minute increments. The basis for this is the Gregorian calendar in use today and the usual 24-hour count. 

The most important points concerning the break in working time are summarized once again:

Year - Consists of 12 ordered months and lasts either 365 days or 366 days in leap year and starts on January 1 and ends on December 31. In the working time recording system
only the years between 1000 and 9999 are considered.

Month - Period of 28, 29, 30 or 31 consecutive days

Day - Always consists of 24 hours of equal length. The time specification for the first hour ranges from 00:00 to 01:00, for the 24th hour from 23:00 to 24:00. 
Counting starts at midnight, but in the counting the minute at 23:59 changes directly to the minute at 00:00 of the following day. 24:00 is only allowed as the end time of a day.

Day of the week - Each day of the year can be assigned one of the 7 days of the week: Monday, Tuesday, Wednesday, Thursday, Friday, Saturday or Sunday.

Week - Period of 7 consecutive days.

Holiday - A day of special holiday rest on which ordinary employees do not work.

Hours - Always consists of 60 minutes of equal length.

Minute - Is not further divided into smaller units

Textual representation<br>
The textual representation of the numeric date formats corresponds to the YYYY-MM-DD form and that of the time information corresponds to the hh:mm form. 
If the two formats are combined, the textual representation corresponds to the YYYY-MM-DDThh:mm form.

Holidays<br>
To read in the dates of the holidays to be taken into account, the working time recording system takes a path to a text file as the only command line argument. 
Only the holidays defined there are taken into account in the working time recording system. The text file contains one or more lines, each line describes a date, which is in the YYYY-MM-DD form. 
An example correct text file could look like this:
1964-01-01
1964-01-20
1964-02-17
1964-03-29
1964-05-25
1964-07-04
1964-09-07
1964-10-12
1964-11-11
1964-11-26
1964-12-25

Employees <br>
Employees are blue-collar and white-collar workers as well as those employed for their vocational training in the medium-sized mechanical engineering company.
Night workers are employees who can perform night work. Night work is any work that involves more than 2 hours of night time. Night time is the time from 23:00 to 6:00 and rest breaks during the night time are
not to be counted as night work.
Production workers are employees who are directly employed to carry out the production process.
The company's employees to be taken into account can be registered in the working time registration system. It is directly and irrevocably indicated whether the employee is a night worker and/or a production worker. 
The employees are registered with their names, consisting of first and last names, and their date of birth. After
successful registration, they are automatically assigned a natural number as a unique identifier. Workers are numbered in order of registration, starting from 1 and ascending, and identified by this number. 
Due to the documentation requirement once successfully registered, employees cannot be removed from the system.

Normative provisions<br>
The daily working time of employees may not exceed 10 hours.

Rest breaks<br>
Work must be interrupted in rest breaks of at least 30 minutes if the working time is 6-9 hours and 45 minutes if the working time exceeds 9 hours in total. 
Rest breaks must be taken in a contiguous period within the corresponding working time. Employees may not be employed for more than 6 consecutive hours without a without a rest break.

Rest period<br>
Employees must have an uninterrupted rest period of at least 11 hours after the end of the daily work period.

Sunday and holiday rest<br>
Employees may not be employed on Sundays and public holidays from 0 a.m. to midnight. Deviating production workers may be employed on Sundays and public holidays in order to prevent an interruption of the production process. 
For employment on Sundays and public holidays, the regulations shall apply apply accordingly, i.e. working hours on Sundays and public holidays may not exceed the specified maximum working hours.

Symbols<br>
role - The role of a worker. Either A for regular workers, N for night workers, P for production workers or NP for night workers who are directly employed for the execution of the production process.<br><br>
name surname - First name and last name of an employee, each beginning with an uppercase letter followed by any number of lowercase letters of the Latin alphabet<br>
birthdate - Date of birth of an employee in the format YYYY-MM-DD<br><br>
id - Natural number as a unique identifier of an employee.<br><br>
time - Date and time specification of a point in time in the format YYYY-MM-DDThh:mm<br><br>
working time - Specification of a duration in the format of a working time hh : mm<br><br>
start end - Start and end time of a working time, each specified in the format of a time YYYY-MM-DDThh:mm<br><br>
startp endp - Start and end time of a rest break, each specified in the format of a time YYYY-MM-DDThh:mm<br><br>

Commands<br>
employee role name surname birthdate - Register a new employee with a certain role. An id is assigned automatically<br><br>
workingtime id start end startp endp - Assign worktime to the employee with the given id as well as a break<br>
workingtime id start end - Assign worktime to the employee with the given id without a break<br><br>
list id - gives back the working times for an employee<br>
eg. worktime start end startp endp<br>
or  worktime start end<br><br>
list worktime - gives back the employees working at that point in time<br>
		the list is sorted in ascending order taking the id into account<br>
eg. name surname date role<br><br>
quit - ends the program
