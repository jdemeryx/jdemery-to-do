jdemery-to-do
=============

Assignment #1 CMPUT301 To-Do List Application

Resources used were mostly a result of doing tutorials to build other projects
and experiment around with the libraries, and then going to start this project.
Documentation of libraries and functions were used to figure out various approaches.
Did not collaborate with anyone. Only library used was GSON in order to store 
the data whenever changes were made. 


Copyright (C) 2014 Jonathan Emery jdemery@ualberta.ca
This program is free software; you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation; either version 2 of the License, or
(at your option) any later version.
This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
GNU General Public License for more details.
You should have received a copy of the GNU General Public License along
with this program; if not, write to the Free Software Foundation, Inc.,
51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.


JSON License:
Copyright (c) 2008-2009 Google Inc.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.



==========================================================================

App is now fully functional, for the most part. All requirements are met,
with the only exceptions being selecting individual ToDos to email. Bulk
Email is working, add/remove/archive/unarchive etc all finished. Could not 
get checkbox's to work properly with my implementation, so coded in a work-
around that seems to be holding up. Counters have a bug; if you archive or
unarchive a todo that is checked off, then the counter doesn't get updated
properly to represent that. 


Accessing the ArchivedToDos is done via the menu option (three dots), as well
as to do the bulk-emailing of the files. Clicking on a ToDo item in either
the main view, or in the archived view cause it to become checked or unchecked
and update the counters appropriately. 

In the main view, clicking on <Archive>, lets you go and select individual ToDos 
to archive, and thus removes them from the main to-do page. Similar process with
<Remove>, just instead it completely deletes the to-do item. 

In the archived view, clicking <Return> allows you to return individual to-do
items back into the "active" section, while <remove> deletes them permanently.




Overall the project could have been modularized a bit more effectively, but
after trying 3-4 different times to modify the scope of the main classes, I
decided it was best to leave them as is. Attempts we made to help lessen the 
overlap, but due to Extends requiring a super() call, there is a bit of 
redundancy in the Activity Classes.

