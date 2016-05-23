var gulp = require('gulp');
var jshint = require('gulp-jshint');
var concat = require('gulp-concat');
var rename = require('gulp-rename');
var uglify = require('gulp-uglify');
var jade = require('gulp-jade');
var minify = require('gulp-minify');

var GulpConfig = {
	jsSrc : ["client/js/common.js","client/js/bnade.js", "ie10-viewport-bug-workaround.js"],
	jsDist : "dist/js",
	jadeSrc : ["client/templates/*.jade", "!client/templates/layout.jade"],
	jadeDist : "dist",
	cssSrc : ["client/css/*.css"],
	cssDist : "dist/css",
	imgSrc : ["client/images/**"],
	imgDist : "dist/images",
}

// css
gulp.task('css', function() {
  return gulp.src(GulpConfig.cssSrc)
  .pipe(gulp.dest(GulpConfig.cssDist));
});

//img
gulp.task('img', function() {
  return gulp.src(GulpConfig.imgSrc)
  .pipe(gulp.dest(GulpConfig.imgDist));
});

// Lint JS
gulp.task('lint', function() {
  return gulp.src(GulpConfig.jsSrc)
    .pipe(jshint())
    .pipe(jshint.reporter('default'));
});

// Concat & Minify JS
gulp.task('minify', function(){
	gulp.src(["client/js/itemsQuery.js","client/js/itemRule.js","client/js/jquery-ui.min.js","client/js/worthItem.js","client/js/wowtoken.js","client/js/topOwner.js","client/js/petQuery.js","client/js/ownerQuery.js","client/js/itemQuery.js","client/js/auctionQuantity.js"])
    .pipe(uglify())
    .pipe(gulp.dest(GulpConfig.jsDist));
  return gulp.src(GulpConfig.jsSrc)
    .pipe(concat('main.js'))
    //.pipe(gulp.dest(GulpConfig.jsDist))
    //.pipe(rename('all.min.js'))
    .pipe(uglify())
    .pipe(gulp.dest(GulpConfig.jsDist));
});

//Concat & Minify JS Dev
gulp.task('minify-dev', function(){
	gulp.src(["client/js/itemsQuery.js","client/js/itemRule.js","client/js/jquery-ui.min.js","client/js/worthItem.js","client/js/wowtoken.js","client/js/topOwner.js","client/js/petQuery.js","client/js/ownerQuery.js","client/js/itemQuery.js","client/js/auctionQuantity.js"])
    .pipe(gulp.dest(GulpConfig.jsDist));
  return gulp.src(GulpConfig.jsSrc)
    .pipe(concat('main.js'))
    .pipe(gulp.dest(GulpConfig.jsDist));
});

//Jade
gulp.task('jade', function() {
	return gulp.src(GulpConfig.jadeSrc)
    	.pipe(jade())
    	.pipe(gulp.dest(GulpConfig.jadeDist));
});

// Jade Dev
gulp.task('jade-dev', function() {
	return gulp.src(GulpConfig.jadeSrc)
    	.pipe(jade({pretty:true}))
    	.pipe(gulp.dest(GulpConfig.jadeDist));
});

//Watch Our Files
gulp.task('watch', function() {
	gulp.watch(GulpConfig.jsSrc, ['lint', 'minify']);
	gulp.watch(GulpConfig.jadeSrc, ['jade']);
});

//Default for dev
gulp.task('default', ['img', 'css', 'jade-dev', 'lint', 'minify-dev', 'watch']);

//prod
gulp.task('prod', ['img', 'css', 'jade', 'lint', 'minify']);