<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class TaskUpload extends Model
{
    use HasFactory;

    protected $casts = [
        'created_at'  => 'date:Y-m-d',
        'updated_at' => 'date:Y-m-d'
    ];

    public function user()
    {
        return $this->belongsTo(User::class)
            ->select([
                "id",
                "full_name",
                "place_enrolled",
                "bdate"
            ]);
    }
}
