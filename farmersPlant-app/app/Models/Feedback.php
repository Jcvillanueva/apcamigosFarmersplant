<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class Feedback extends Model
{
    use HasFactory;

    protected $table = "feedbacks";

    protected $casts = [
        'created_at'  => 'date:Y-m-d',
        'updated_at' => 'date:Y-m-d'
    ];

    public function task()
    {
        return $this->belongsTo(Task::class)
            ->select([
                "id",
                "name"
            ]);
    }
}
