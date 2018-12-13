package com.artefacto.microformas;

public class EntityCatalog
{
	public EntityCatalog()
	{
        mBrandConnectivityId = Integer.MIN_VALUE;
        mModelSoftwareId = Integer.MIN_VALUE;
		mCount = Integer.MIN_VALUE;
	}

    public int getBrandConnectivityId() {
        return mBrandConnectivityId;
    }

    public void setBrandConnectivityId(int brandConnectivityId) {
        this.mBrandConnectivityId = brandConnectivityId;
    }

    public int getModelSoftwareId() {
        return mModelSoftwareId;
    }

    public void setModelSoftwareId(int modelSoftwareId) {
        this.mModelSoftwareId = modelSoftwareId;
    }
	
	public int getCount() {
		return mCount;
	}
	
	public void setCount(int count) {
		this.mCount = count;
	}

    public String getType() {
        return mType;
    }

    public void setType(String mType) {
        this.mType = mType;
    }

    @Override
	public String toString()
	{
		return mBrandConnectivityId + "," + mModelSoftwareId + "," + mCount;
	}

	private int mBrandConnectivityId;
	private int mModelSoftwareId;
	private int mCount;

    private String mType;
}
