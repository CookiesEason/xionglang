
//域名列表
var host=document.domain;
host=host.substr(host.indexOf("."));
var COOKIE_DOMAIN = host;
var DOMAIN = host+"/";
var WWW_DOMAIN = 'http://www' + DOMAIN;
var NEWS_DOMAIN =  'http://news' + DOMAIN;
var MEMBER_DOMAIN =  'http://member' + DOMAIN;
var IMG_DOMAIN = 'http://img' + DOMAIN;
var LOGIN_DOMAIN = 'http://login' + DOMAIN;
var REG_DOMAIN = 'http://reg' + DOMAIN;
var SAFE_DOMAIN = 'http://safe' + DOMAIN;
var S_DOMAIN = 'http://s' + DOMAIN;
var LIVE_DOMAIN = 'http://live'  + DOMAIN;
var ITEM_DOMAIN = 'http://item' + DOMAIN;
var M_DOMAIN = 'http://m' + DOMAIN;
var V_DOMAIN = 'http://v' + DOMAIN;
var BBS_DOMAIN = 'http://bbs' + DOMAIN;
var I_DOMAIN = 'http://i' + DOMAIN;
var FUND_DOMAIN = 'http://fund' + DOMAIN;
var BAOXIAN_DOMAIN = 'http://baoxian' + DOMAIN;
var PEIZI_DOMAIN = 'http://peizi' + DOMAIN;
var MANAGE_DOMAIN = 'http://manage' + DOMAIN;
var CREDIT_DOMAIN = 'http://credit' + DOMAIN;
var LOAN_DOMAIN = 'http://loan' + DOMAIN;

//document.domain = host.substr(1);

function logout() {
    jQuery.getJSON(BBS_DOMAIN+"member.php?mod=logging&action=logout&formhash=f89582d4&dosubmit=1&notbbs=yes&callback=?",
        function(data){ 
            if(data != null && data != ""){
                if (data.resStatusCode != 'success') {
                } else { 
                    window.location.reload(); 
                }
            }
        }
        );
}


var isMobile = {
    Android: function() {
        return navigator.userAgent.match(/Android/i) ? true : false; 
    },
    BlackBerry: function() {
        return navigator.userAgent.match(/BlackBerry/i) ? true : false;
    },
    iOS: function() {
        return navigator.userAgent.match(/iPhone|iPad|iPod/i) ? true : false;
    },
    Windows: function() {
        return navigator.userAgent.match(/IEMobile/i) ? true : false;
    },
    any: function() {
        return (isMobile.Android() || isMobile.BlackBerry() || isMobile.iOS() || isMobile.Windows());
    }
};


