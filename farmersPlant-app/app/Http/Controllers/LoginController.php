<?php

namespace App\Http\Controllers;

use App\Models\{
    User
};
use Illuminate\Http\Request;

class LoginController extends Controller
{
    public function login(Request $request) {
        try {
            $user = User::where('email', $request->get('email'))
                ->where('password', $request->get('password'))
                ->first();
            if (!$user) {
                return response()->json(['error' => "Invalid Password."], 404);
            }
        } catch (Exception $e) {
            return response()->json(['error' => $e->getMessage()], 500);
        }

        return response()->json(
            [
                'data' => $user
            ], 
            201
        );
    }
}
