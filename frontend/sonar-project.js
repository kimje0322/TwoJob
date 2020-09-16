const sonarqubeScanner = require('sonarqube-scanner');
     sonarqubeScanner({
       serverUrl: 'http://j3b102.p.ssafy.io:8000',
       options : {
       'sonar.sources': '.'
       }
     }, () => {});