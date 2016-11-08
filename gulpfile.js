var gulp = require('gulp');
var jshint = require('gulp-jshint');
var concat = require('gulp-concat');
var rename = require('gulp-rename');
var uglify = require('gulp-uglify');
var jade = require('gulp-jade');
var minify = require('gulp-minify');
var rev = require('gulp-rev-append');
var minifyCss = require('gulp-minify-css');

var jsDir = "client/js";
var GulpConfig = {
	jsSrc: ["client/js/*.js"],
    mainJsSrc: [jsDir+"/lib/jquery-ui.min.js","client/js/main/*.js"],
    jsDist: "dist/js",
    jadeSrc: ["client/templates/*.jade", "!client/templates/layout.jade"],
    jadeDist: "dist/",
    htmlSrc: ['dist/*.html'],
    htmlDist: "dist/",
    cssSrc: ["client/css/*.css"],
    cssDist: "dist/css",
    imgSrc: ["client/images/**"],
    imgDist: "dist/images",
}

// css
gulp.task('css', function () {
	// 复制字体文件
	gulp.src(["client/fonts/**"]).pipe(gulp.dest("dist/fonts"));
    return gulp.src(GulpConfig.cssSrc)
		.pipe(concat("main.css"))
		.pipe(minifyCss())
        .pipe(gulp.dest(GulpConfig.cssDist));
});

//img
gulp.task('img', function () {
    return gulp.src(GulpConfig.imgSrc)
        .pipe(gulp.dest(GulpConfig.imgDist));
});

// Lint JS
gulp.task('lint', function () {
    return gulp.src(GulpConfig.jsSrc)
        .pipe(jshint())
        .pipe(jshint.reporter('default'));
});

// Concat & Minify JS
gulp.task('minify', function () {
	gulp.src(GulpConfig.mainJsSrc)
        .pipe(concat('main.js'))
        //.pipe(gulp.dest(GulpConfig.jsDist))
        //.pipe(rename('all.min.js'))
        .pipe(uglify())
        .pipe(gulp.dest(GulpConfig.jsDist));
    return gulp.src(GulpConfig.jsSrc)
        .pipe(uglify())
        .pipe(gulp.dest(GulpConfig.jsDist));
});

//Concat & Minify JS Dev
gulp.task('minify-dev', function () {
	gulp.src(GulpConfig.mainJsSrc)
        .pipe(concat('main.js'))
        .pipe(gulp.dest(GulpConfig.jsDist));
    return gulp.src(GulpConfig.jsSrc)
        .pipe(gulp.dest(GulpConfig.jsDist));
});

//Jade
gulp.task('jade', function () {
    return gulp.src(GulpConfig.jadeSrc)
        .pipe(jade({pretty: true}))
        .pipe(gulp.dest(GulpConfig.jadeDist));
});

// Jade Dev
gulp.task('jade-dev', function () {
    return gulp.src(GulpConfig.jadeSrc)
        .pipe(jade({pretty: true}))
        .pipe(gulp.dest(GulpConfig.jadeDist));
});

//Watch Our Files
gulp.task('watch', function () {
    gulp.watch(GulpConfig.jsSrc, ['lint', 'minify']);
    gulp.watch(GulpConfig.jadeSrc, ['jade']);
});

//Default for dev
gulp.task('default', ['img', 'css', 'jade-dev', 'lint', 'minify-dev', 'watch']);

//prod
// 前端资源文件增加hash 用于清除缓存
gulp.task('prod', ['img', 'css', 'jade', 'lint', 'minify'], function () {
    gulp.src(GulpConfig.htmlSrc)
        .pipe(rev())
        .pipe(gulp.dest(GulpConfig.htmlDist));
});