/*
Copyright (c) 2003-2012, CKSource - Frederico Knabben. All rights reserved.
For licensing, see LICENSE.html or http://ckeditor.com/license
 */
 //获取根目录（域名+工程名）
 function getBasePath() {
    var curWwwPath = window.document.location.href;
    var pathName = window.document.location.pathname;
    var pos = curWwwPath.indexOf(pathName);
    var localhostPaht = curWwwPath.substring(0, pos);
    var projectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1);
    return (localhostPaht);
 }
CKEDITOR.editorConfig = function(config) {
	config.toolbar = 'MyToolbar';
	config.toolbar_MyToolbar =
        [
                { name: 'document', items : [ 'Preview' ] },
            
            { name: 'clipboard', items : [ 'Cut','Copy','Paste','PasteText','PasteFromWord','-','Undo','Redo' ] },
                	 { name: 'editing', items : [ 'Find','Replace','-','SelectAll','-','Scayt' ] },
                { name: 'styles', items : [ 'Styles','Format' ] },
                {name:'colors',items:['TextColor']},
                { name: 'paragraph', items : [ 'NumberedList','BulletedList','-','Outdent','Indent','-','Blockquote' ] },
            { name: 'insert', items :[ 'Image','Flash','Table','HorizontalRule','Smiley','SpecialChar','PageBreak'
                 ,'Iframe' ] },
                { name: 'links', items : [ 'Link','Unlink','Anchor' ] },
              
        ];
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	// Define changes to default configuration here. For example:
	// config.language = 'fr';
	// config.uiColor = '#AADC6E';
	var path = getBasePath()+"/upload";
	config.filebrowserUploadUrl=path;
	config.height = 300; 
};
 


 

 
