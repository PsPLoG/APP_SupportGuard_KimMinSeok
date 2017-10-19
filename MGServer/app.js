var express = require('express'),
    mysql = require('mysql'),
    formidable = require('formidable'),
    fs = require('fs'),
    moment = require('moment');

var app = express();
var connection = mysql.createConnection({
    host: 'localhost',
    query: {
        pool: true
    },
    user: 'root',
    password: 'root',
    database: 'OSAM'
});

app.get('/', function(request, response) {
    response.send('hello world');
    response.end();
});

// --------------------------------------------------------------------------------------
// 출입로그 
// --------------------------------------------------------------------------------------
app.get('/passlog', function(req,res){
	var sql = 'SELECT * FROM OSAM.kimminseok_passlog where aCode=' + req.query.aCode;
    connection.query(sql, function(err, rows, fields) {
        if (err) {
            res.sendStatus(400);
            return;
        }
        if (rows.length == 0) {
            res.sendStatus(204);
        } else {
        	res.status(201).send(rows);
            res.end();
        }
    });
});
app.get('/passlogperson', function(req,res){
	var sql = 'SELECT * FROM OSAM.kimminseok_passlog where IdCode=' + req.query.idCode;
	console.log('passlogperson : '+sql);
    connection.query(sql, function(err, rows, fields) {
        if (err) {
            res.sendStatus(400);
            return;
        }
        if (rows.length == 0) {
            res.sendStatus(204);
        } else {
        	res.status(201).send(rows);
            res.end();
        }
    });
});


// --------------------------------------------------------------------------------------
// 로그인후 데이터 전송
// --------------------------------------------------------------------------------------
app.get('/loadData/', function(req, res) {
	// id에 해당하는 비밀번호 불러옴;
    var sql = 'select * from kimminseok_passuser where ID=' + req.query.id;
    connection.query(sql, function(err, rows, fields) {
        if (err) {
            res.sendStatus(400);
            return;
        }
        if (rows.length == 0) {
            res.sendStatus(204);
        } else {
        	//console.log(rows[0].Pass);
        	if(rows[0].Pass==req.query.pass){
        		var data = 'true\n'+
	        	rows[0].IdCode+'\n'
	        	+rows[0].ID+'\n'
	        	+rows[0].Rank+'\n'
	        	+rows[0].aType+'\n'
	        	+rows[0].affiliation+'\n'
	        	+rows[0].aCode+'\n'
	        	+rows[0].pName+'\n'
	        	+rows[0].ImgSrc+'\n';
	        	res.send(data);
	            res.end();
       		}
        	else res.status(201).send(false);
            res.end();
        }
    });

});
// --------------------------------------------------------------------------------------
// 위병소에서 바코드 찍을시 ㄷ이터 로드 
// --------------------------------------------------------------------------------------
app.get('/loadGData/', function(req, res) {
	// id에 해당하는 비밀번호 불러옴;
    var sql = 'select * from kimminseok_passuser where IdCode=' + req.query.idcode;
    connection.query(sql, function(err, rows, fields) {
        if (err) {
            res.sendStatus(400);
            return;
        }
        if (rows.length == 0) {
            res.sendStatus(204);
        } else {
        	//console.log(rows[0].Pass);
    		var data = 'true\n'
        	+rows[0].ID+'\n'
        	+rows[0].affiliation+'\n'
        	+rows[0].pName+'\n';
        	console.log(data);
        	res.send(data);
            res.end();
        }
    });

});
// --------------------------------------------------------------------------------------
// 위병소 출입 신청시 db에 로그 등록 및 퇴영 처리 
// "/pass/?idCode="+idCode+"&aCode="+aCode);
// IdCode integer not null comment 'ID',
// passTime datetime not null comment '출입시간',
// Passtype Text not null comment '출입종류 출입기록 1 입영 2 퇴영',
// aCode text not null comment '부대코드');
//  UPDATE `OSAM`.`passuser` SET `ID`='151234' WHERE `IdCode`='1';
// UPDATE `OSAM`.`passlog` SET `Passtype`='2' WHERE `num`='1';
// 퇴영 , num ,,,,
// --------------------------------------------------------------------------------------
app.get('/pass/', function(req, res) {
	// id에 해당하는 비밀번호 불러옴;
	var time = moment().format('YYYY-MM-DD h:mm:ss');
	var sql 
	if(req.query.type=='입영'){
    	sql = 'INSERT INTO kimminseok_passlog (IdCode, pName,PassTime, passType, aCode) VALUES ('+req.query.idCode+',\''+req.query.pName+'\',\''+
    																			time+'\','+
    																			'\'1\','+
    																			req.query.aCode+')';
    }else 
    	sql = 'UPDATE kimminseok_passlog SET Passtype=2 WHERE num='+req.query.idCode;
    console.log(sql);
    connection.query(sql, function(err, rows, fields) {
        if (err) {
            res.sendStatus(400);
            return;
        }
        if (rows.length == 0) {
            res.sendStatus(204);
        } else {
        	console.log(sql);
        	res.sendStatus(201);
            res.end();
        }
    });

});



var savePath = 'c://mp//image//';

var isFormData = function(req) {
    var type = req.headers['content-type'] || '';
    return 0 == type.indexOf('multipart/form-data');
}

// --------------------------------------------------------------------------------------
// 이미지 다운로드
// --------------------------------------------------------------------------------------
app.get('/image/:filename', function(req, res) {

    var path = savePath + req.params.filename;
    fs.exists(path, function(exists) {
        if (exists) {
        	console.log('exists')
            var stream = fs.createReadStream(savePath + req.params.filename);
            stream.pipe(res);
            stream.on('close', function() {
                res.end();
            });
        } else {
        	console.log('204')
            res.sendStatus(204);
        }
    });
});

app.listen(5010);