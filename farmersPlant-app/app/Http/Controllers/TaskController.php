<?php
namespace App\Http\Controllers;

use App\Models\{
    Task,
    TaskUpload
};
use Illuminate\Http\Request;
use Illuminate\Support\Facades\{
    Storage
};

class TaskController extends Controller
{
    public function all(String $user_id)
    {
        try {
            $tasks = Task::all();
            $taskUploadIds = TaskUpload::where('user_id', $user_id)
                ->groupBy('task_id')
                ->get();
        } catch (Exception $e) {
            return response()->json(['error' => $e->getMessage()], 500);
        }

        return response()->json(
            [
                'data' => [
                    'tasks' => $tasks,
                    'uploaded' => $taskUploadIds
                ]
            ], 
            201
        );
    }

    public function taskNames(String $user_id, Request $request)
    {
        try {
            $task = Task::where('user_id', $user_id)
                ->get();
        } catch (Exception $e) {
            return response()->json(['error' => $e->getMessage()], 500);
        }

        return response()->json(
            [
                'data' => $task
            ], 
            201
        );
    }

    public function taskName(Request $request)
    {
        try {
            $isTaskExist = Task::where('user_id', $request->get('user_id'))
                ->where('name', $request->get('name'))
                ->first();

            if ($isTaskExist) {
                return response()->json(['error' => "Task already exists."], 500);
            }

            $task = new Task;
            $task->user_id = $request->get('user_id');
            $task->name = $request->get('name');
            $task->save();
        } catch (Exception $e) {
            return response()->json(['error' => $e->getMessage()], 500);
        }

        return response()->json(
            [
                'data' => $task
            ], 
            201
        );
    }

    public function deleteName(Request $request)
    {
        try {
            $task = Task::where('id', $request->get('id'))
                ->first();
            $task->delete();
        } catch (Exception $e) {
            return response()->json(['error' => $e->getMessage()], 500);
        }

        return response()->json(
            [
                'data' => $task
            ], 
            201
        );
    }

    public function tasks(String $user_id, Request $request)
    {
        try {
            $taskUpload = TaskUpload::where('user_id', $user_id)
                ->get();
        } catch (Exception $e) {
            return response()->json(['error' => $e->getMessage()], 500);
        }

        return response()->json(
            [
                'data' => $taskUpload
            ], 
            201
        );
    }

    public function images(String $user_id, String $task_id, Request $request)
    {
        try {
            $taskUpload = TaskUpload::where('user_id', $user_id)
                ->where('task_id', $task_id)
                ->get();
        } catch (Exception $e) {
            return response()->json(['error' => $e->getMessage()], 500);
        }

        return response()->json(
            [
                'data' => $taskUpload
            ], 
            201
        );
    }

    public function farmers(String $user_id, String $task_id, Request $request)
    {
        try {
            $taskUploads = TaskUpload::where('task_id', $task_id)
                ->where('is_graded', 0)
                ->with('user')
                ->groupBy('user_id')
                ->get();
        } catch (Exception $e) {
            return response()->json(['error' => $e->getMessage()], 500);
        }

        return response()->json(
            [
                'data' => $taskUploads
            ], 
            201
        );
    }

    public function upload(Request $request)
    {
        try {
           
            $file = $request->file('file');
            $path = "/" . $request->get('user_id');
            $task_name = str_replace(" ", "_", $file->getClientOriginalName());

            $isFileExistForUser = TaskUpload::where('user_id', $request->get('user_id'))
                ->where('task_id', $request->get('task_id'))
                ->where('task_name', $task_name)
                ->first();

            if ($isFileExistForUser) {
                return response()->json(['error' => "File already exists."], 500);
            }

            $storage = Storage::disk('public')->putFileAs($path, $file, $task_name);
            $taskUpload = new TaskUpload;
            $taskUpload->user_id = $request->get('user_id');
            $taskUpload->task_id = $request->get('task_id');
            $taskUpload->task_path = $storage;
            $taskUpload->task_name = $task_name;
            $taskUpload->save();
        } catch (Exception $e) {
            return response()->json(['error' => $e->getMessage()], 500);
        }

        return response()->json(
            [
                'data' => $taskUpload
            ], 
            201
        );
    }

    public function delete(Request $request)
    {
        try {
            $taskUpload = TaskUpload::where('id', $request->get('id'))
                ->first();
            unlink(storage_path("app\\public\\" . $request->get('user_id'). "\\" . $taskUpload->task_name));
            $taskUpload->delete();
        } catch (Exception $e) {
            return response()->json(['error' => $e->getMessage()], 500);
        }

        return response()->json(
            [
                'data' => $taskUpload
            ], 
            201
        );
    }

}
