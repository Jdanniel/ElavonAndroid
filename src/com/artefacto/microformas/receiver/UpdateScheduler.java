package com.artefacto.microformas.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.artefacto.microformas.services.GetUpdatesService;

public class UpdateScheduler extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent)
    {
		if (GetUpdatesService.isUpdating)
		{
			return;
		}
        else
        {
			Intent service = new Intent(context, GetUpdatesService.class);
			context.startService(service);
		}
	}

}
