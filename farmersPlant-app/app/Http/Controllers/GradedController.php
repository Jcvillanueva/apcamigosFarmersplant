<?php

namespace App\Http\Controllers;

use App\Models\{
    Feedback,
    User
};
use Illuminate\Http\Request;

class GradedController extends Controller
{
    public function farmers(Request $request)
    {
        try {
            $feedbackuserIds = Feedback::groupBy("user_id")
                ->get()
                ->pluck('user_id');
            $users = User::whereIn("id", $feedbackuserIds)
                ->get();
        } catch (Exception $e) {
            return response()->json(['error' => $e->getMessage()], 500);
        }

        return response()->json(
            [
                'data' => $users
            ], 
            200
        );
    }
}
