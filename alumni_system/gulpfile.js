'use strict';

var gulp = require('gulp');
var babel = require('gulp-babel');
var concat = require('gulp-concat');
var sass = require('gulp-sass');
var sourcemaps = require('gulp-sourcemaps');

var resourcesDir = "./resource"
var assetsDir = "./web/assets";

var babelSource = resourcesDir + '/script/**/*.js';
var sassSource = resourcesDir + '/sass/**/*.scss';

gulp.task('default', function() {

});

gulp.task('babel', function () {
    return gulp.src(babelSource)
        .pipe(sourcemaps.init())
        .pipe(babel({
            presets: ['es2015']
        }))
        .pipe(concat('script.js'))
        .pipe(sourcemaps.write('./'))
        .pipe(gulp.dest(assetsDir + '/js'));
});

gulp.task('sass', function () {
    return gulp.src(sassSource)
        .pipe(sourcemaps.init())
        .pipe(sass().on('error', sass.logError))
        .pipe(sourcemaps.write('./'))
        .pipe(gulp.dest(assetsDir + '/css'));
});

gulp.task('watch', function () {
    gulp.watch(sassSource, ['sass']);
    gulp.watch(babelSource, ['babel']);
});

