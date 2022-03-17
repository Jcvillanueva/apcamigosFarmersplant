<?php

namespace App\Http\Controllers;

use App\Models\{
    Feedback,
    TaskUpload
};
use Illuminate\Http\Request;

class FeedbackController extends Controller
{
    public function submit(Request $request)
    {
        try {
            $feedback = new Feedback;
            $feedback->user_id = $request->get('user_id');
            $feedback->task_id = $request->get('task_id');
            $feedback->feedback = $request->get('feedback');
            $feedback->save();

            TaskUpload::where('user_id', $request->get('user_id'))
                ->where('task_id', $request->get('task_id'))
                ->update([
                    'is_graded' => 1
                ]);
        } catch (Exception $e) {
            return response()->json(['error' => $e->getMessage()], 500);
        }

        return response()->json(
            [
                'data' => $feedback
            ], 
            201
        );
    }

    public function farmer(String $user_id,Request $request)
    {
        try {
            $feedbacks = Feedback::where('user_id', $user_id)
                ->with('task')
                ->get();
        } catch (Exception $e) {
            return response()->json(['error' => $e->getMessage()], 500);
        }

        return response()->json(
            [
                'data' => $feedbacks
            ], 
            200
        );
    }
}
