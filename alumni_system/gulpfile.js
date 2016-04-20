'use strict';

var gulp = require('gulp');
var concat = require('gulp-concat');
var sass = require('gulp-sass');
var sourcemaps = require('gulp-sourcemaps');
var source = require('vinyl-source-stream');
var buffer = require('vinyl-buffer');
var browserify = require('browserify');
var watchify = require('watchify');
var babelify = require('babelify');
var exit = require('gulp-exit');

var resourcesDir = "./resource"
var assetsDir = "./web/assets";

var es6Main = resourcesDir + '/script/Main.js';
var sassSource = resourcesDir + '/sass/**/*.scss';

function compilees6(watch) {
    var bundler = watchify(browserify(es6Main, { debug: true }).transform(babelify.configure({
        "presets": ["es2015"]
    })));

    function rebundle() {
        return bundler.bundle()
            .on('error', function(err) { console.error(err); this.emit('end'); })
            .pipe(source('script.js'))
            .pipe(buffer())
            .pipe(sourcemaps.init({ loadMaps: true }))
            .pipe(sourcemaps.write('./'))
            .pipe(gulp.dest(assetsDir + "/js"));
    }

    if (watch) {
        bundler.on('update', function() {
            console.log('-> bundling...');
            rebundle();
        });

        rebundle();
    } else {
        rebundle().pipe(exit());
    }
}

function watches6() {
    return compilees6(true);
};

function compliesass() {
    return gulp.src(sassSource)
        .pipe(sourcemaps.init())
        .pipe(sass().on('error', sass.logError))
        .pipe(sourcemaps.write('./'))
        .pipe(gulp.dest(assetsDir + '/css'));
}

gulp.task('build', function() { return compilees6(); });
gulp.task('watch-build', function() { return watches6(); });
gulp.task('sass', function () { return compliesass(); });
gulp.task('watch-sass', function () { return gulp.watch(sassSource, ['sass']); });
