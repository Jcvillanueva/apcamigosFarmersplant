<?php

/*
|--------------------------------------------------------------------------
| API Routes
|--------------------------------------------------------------------------
|
| Here is where you can register API routes for your application. These
| routes are loaded by the RouteServiceProvider within a group which
| is assigned the "api" middleware group. Enjoy building your API!
|
*/

use Illuminate\Http\Request;
use Illuminate\Support\Facades\Route;
use App\Http\Controllers\{
    FeedbackController,
    GradedController,
    LoginController,
    PDFController,
    RegisterController,
    TaskController
};

Route::middleware('api')->name('api')->group(function () {
    Route::post('/register', [RegisterController::class, 'register'])->name('register.register');
    Route::post('/profile', [RegisterController::class, 'profile'])->name('register.profile');
    Route::post('/login', [LoginController::class, 'login'])->name('login.login');

    Route::get('/pdf/{user_id}', [PDFController::class, 'modules'])->name('pdf.modules');
    Route::post('/pdf', [PDFController::class, 'upload'])->name('pdf.upload');
    Route::delete('/pdf', [PDFController::class, 'delete'])->name('pdf.delete');
    Route::get('/pdf/get/all', [PDFController::class, 'all'])->name('pdf.all');

    Route::get('/taskname/{user_id}', [TaskController::class, 'taskNames'])->name('task.modules');
    Route::post('/taskname', [TaskController::class, 'taskName'])->name('task.upload');
    Route::delete('/taskname', [TaskController::class, 'deleteName'])->name('task.deleteName');

    Route::get('/task/{user_id}', [TaskController::class, 'tasks'])->name('task.tasks');
    Route::get('/task/images/{user_id}/{task_id}', [TaskController::class, 'images'])->name('task.images');
    Route::get('/task/farmers/all/{user_id}/{task_id}', [TaskController::class, 'farmers'])->name('task.farmers');
    Route::post('/task', [TaskController::class, 'upload'])->name('task.upload');
    Route::delete('/task', [TaskController::class, 'delete'])->name('task.delete');

    Route::get('/farmer/task/get/all/{user_id}', [TaskController::class, 'all'])->name('task.all');

    Route::get('/graded', [GradedController::class, 'farmers'])->name('graded.farmers');

    Route::get('/feedback/{user_id}', [FeedbackController::class, 'farmer'])->name('feedback.farmer');
    Route::post('/feedback', [FeedbackController::class, 'submit'])->name('feedback.submit');
});
