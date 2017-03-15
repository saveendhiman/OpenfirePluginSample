# OpenfirePluginSample

[![Twitter](https://img.shields.io/badge/Twitter-@saveendhiman-blue.svg?style=flat)](https://twitter.com/saveendhiman)

[![License](https://img.shields.io/badge/license-Apache%202-4EB1BA.svg)](https://www.apache.org/licenses/LICENSE-2.0.html)

Please follow below steps to make openfire plguin.

With the help of plugin you can communicate with database using xmpp.

Need to install openfire on your local machine or remote.
Please follow this link to get it done.

* [Download Openfire] (http://download.igniterealtime.org/openfire/docs/latest/documentation/install-guide.html) from here.


1. Import Openfire Source to eclipse.
Openfire project need a path of some libraries that are present in lib folder.

Please follow this video to include all libraries to openfire.

* [Download Openfire] (https://www.youtube.com/watch?v=KlMaBplFbuQ) from here.

2. Import openfire plugin sample to eclipse then add openfire source to its build path.


3. Now do one thing download openfire code from here.

https://www.igniterealtime.org/downloads/source.jsp

4. Copy your plugin code to Go openfire source -> target -> openfire -> 

Src -> plugins 

Paste it here inside this folder.


Now compilation need to do this plugin.


Go to command or you can make .bat file for compilation.

Go to this your Openfire Source folder.Then 

cd D:\Openfire\openfire

ant -f build/build.xml clean openfire plugin -Dplugin=openfire

Write this command your command line.It compile your plguin.Now your plugin jar file created in 

Openfire Source -> target -> openfire -> plugins

** Upload this plugin to openfire console.


5. Import XMPP_Studio android project in studio.
Now try to test with add delete and update etc.

Your entry is directly added deleted and updated from database using xmpp.


Thanks


##DONATIONS

This project needs you! If you would like to support this project's further development, the creator of this project or the continuous maintenance of this project, feel free to donate. Your donation is highly appreciated (and I love food, coffee and beer). Thank you!

**PayPal**

* **[Donate $5]**: Thank's for creating this project, here's a coffee (or some beer) for you!

* **[Donate $10]**: Wow, I am stunned. Let me take you to the movies!ù

* **[Donate $15]**: I really appreciate your work, let's grab some lunch!

* **[Donate $25]**: That's some awesome stuff you did right there, dinner is on me!

* **[Donate $50]**: I really really want to support this project, great job!

* **[Donate $100]**: You are the man! This project saved me hours (if not days) of struggle and hard work, simply awesome!

* **[Donate $2799]**: Go buddy, buy Macbook Pro for yourself!

Of course, you can also choose what you want to donate, all donations are awesome!!


#Start from

minSdkVersion 14

#LICENSE

Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.

#Authors

[Saveen Dhiman] (https://github.com/saveendhiman) original Author


[Donate $5]: 		https://www.paypal.me/saveendhiman/5
[Donate $10]:  		https://www.paypal.me/saveendhiman/10
[Donate $15]:  		https://www.paypal.me/saveendhiman/15
[Donate $25]:  		https://www.paypal.me/saveendhiman/25
[Donate $50]: 		https://www.paypal.me/saveendhiman/50
[Donate $100]: 		https://www.paypal.me/saveendhiman/100
[Donate $2799]: 	https://www.paypal.me/saveendhiman/2799



