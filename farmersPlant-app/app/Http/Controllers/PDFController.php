<?php

namespace App\Http\Controllers;

use App\Models\{
    FileUpload
};
use Illuminate\Http\Request;
use Illuminate\Support\Facades\{
    Storage
};

class PDFController extends Controller
{
    public function all()
    {
        try {
            $fileUploads = FileUpload::all();
        } catch (Exception $e) {
            return response()->json(['error' => $e->getMessage()], 500);
        }

        return response()->json(
            [
                'data' => $fileUploads
            ], 
            201
        );
    }

    public function modules(String $user_id, Request $request)
    {
        try {
            $fileUpload = FileUpload::where('user_id', $user_id)
                ->get();
        } catch (Exception $e) {
            return response()->json(['error' => $e->getMessage()], 500);
        }

        return response()->json(
            [
                'data' => $fileUpload
            ], 
            201
        );
    }

    public function upload(Request $request)
    {
        try {
           
            $file = $request->file('file');
            $path = "/" . $request->get('user_id');
            $file_name = str_replace(" ", "_", $file->getClientOriginalName());

            $isFileExistForUser = FileUpload::where('user_id', $request->get('user_id'))
                ->where('file_name', $file_name)
                ->first();

            if ($isFileExistForUser) {
                return response()->json(['error' => "File already exists."], 500);
            }

            $storage = Storage::disk('public')->putFileAs($path, $file, $file_name);
            $fileUpload = new FileUpload;
            $fileUpload->user_id = $request->get('user_id');
            $fileUpload->file_path = $storage;
            $fileUpload->file_name = $file_name;
            $fileUpload->save();
        } catch (Exception $e) {
            return response()->json(['error' => $e->getMessage()], 500);
        }

        return response()->json(
            [
                'data' => $fileUpload
            ], 
            201
        );
    }

    public function delete(Request $request)
    {
        try {
            $fileUpload = FileUpload::where('id', $request->get('id'))
                ->first();
            unlink(storage_path("app\\public\\" . $request->get('user_id'). "\\" . $fileUpload->file_name));
            $fileUpload->delete();
        } catch (Exception $e) {
            return response()->json(['error' => $e->getMessage()], 500);
        }

        return response()->json(
            [
                'data' => $fileUpload
            ], 
            201
        );
    }
}
