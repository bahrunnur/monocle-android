package com.bahrunnur.monocle;

import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.ViewById;

@EActivity(R.layout.about)
public class AboutActivity extends SherlockActivity {

	@ViewById(R.id.about_created_by)
	TextView mAboutCreatedBy;
	
	@AfterViews
	public void afterViews() {
		ActionBar bar = getSupportActionBar();
		bar.setDisplayHomeAsUpEnabled(true);
		
		mAboutCreatedBy.setMovementMethod(LinkMovementMethod.getInstance());
		mAboutCreatedBy.setText(Html.fromHtml("created by: <a href=\"http://twitter.com/bahrunnur\">@bahrunnur</a>"));
	}
	
}
