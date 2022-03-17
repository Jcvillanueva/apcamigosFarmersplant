<?php

namespace App\Http\Controllers;

use App\Models\{
    Address,
    User
};
use Illuminate\Http\Request;

class RegisterController extends Controller
{
    public function register(Request $request) {
        try {
            $user = new User;
            $user->full_name = $request->get('fullname');
            $user->place_enrolled = $request->get('place_enrolled');
            $user->username = $request->get('username');
            $user->email = $request->get('email');
            $user->password = $request->get('password');
            $user->save();
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

    public function profile(Request $request) {
        try {
            $user = User::find($request->get('user_id'));
            $user->bdate = $request->get('bdate');
            $user->save();
            $address = Address::where('user_id', $user->id)
                ->first();
            if (!$address) {
                $address = new Address;
            }
            $address->user_id = $user->id;
            $address->house_no = $request->get('house_no');
            $address->street_1 = $request->get('street_1');
            $address->street_2 = $request->get('street_2');
            $address->barangay = $request->get('barangay');
            $address->city = $request->get('city');
            $address->zip = $request->get('zip');
            $address->save();
        } catch (Exception $e) {
            return response()->json(['error' => $e->getMessage()], 500);
        }

        return response()->json(
            [
                'data' =>[
                    'user' => $user,
                    'address' => $address,
                ]
            ], 
            201
        );
    }
}
