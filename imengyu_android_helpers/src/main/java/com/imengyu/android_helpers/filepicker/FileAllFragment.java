package com.imengyu.android_helpers.filepicker;

import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hjq.permissions.OnPermissionCallback;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;
import com.imengyu.android_helpers.R;
import com.imengyu.android_helpers.filepicker.adapter.AllFileAdapter;
import com.imengyu.android_helpers.filepicker.adapter.OnFileItemClickListener;
import com.imengyu.android_helpers.filepicker.model.FileEntity;
import com.imengyu.android_helpers.filepicker.util.FileUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 全部文件
 */

public class FileAllFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private TextView mEmptyView,tv_back;
    private String mPath;
    private String rootPath;
    private List<FileEntity> mListFiles;
    private FileSelectFilter mFilter;
    //筛选类型条件
    private final String[] mFileTypes = new String[]{};
    private AllFileAdapter mAllFileAdapter;
    private OnUpdateDataListener mOnUpdateDataListener;

    public void setOnUpdateDataListener(OnUpdateDataListener onUpdateDataListener) {
        mOnUpdateDataListener = onUpdateDataListener;
    }
    public static FileAllFragment newInstance(){
        return new FileAllFragment();
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_file_all,null);
        initView(view);
        initData();
        initEvent();
        return view;
    }

    private void initView(View view) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rl_all_file);
        mRecyclerView.setLayoutManager(layoutManager);
        mEmptyView = (TextView) view.findViewById(R.id.empty_view);
        tv_back = (TextView) view.findViewById(R.id.tv_back);
    }

    private void initData() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            XXPermissions.with(this)
                    .permission(Permission.MANAGE_EXTERNAL_STORAGE)
                    .request(new OnPermissionCallback() {
                        @Override
                        public void onGranted(List<String> permissions, boolean all) {
                            getData();
                        }
                        @Override
                        public void onDenied(List<String> permissions, boolean never) {
                            Toast.makeText(getContext(),"读写文件权限被拒绝",Toast.LENGTH_LONG).show();
                        }
                    });
        } else {
            XXPermissions.with(this)
                    .permission(Permission.Group.STORAGE)
                    .request(new OnPermissionCallback() {
                        @Override
                        public void onGranted(List<String> permissions, boolean all) {
                            getData();
                        }
                        @Override
                        public void onDenied(List<String> permissions, boolean never) {
                            Toast.makeText(getContext(),"读写文件权限被拒绝",Toast.LENGTH_LONG).show();
                        }
                    });
        }
    }

    private void getData(){
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            Toast.makeText(getContext(), R.string.not_available, Toast.LENGTH_SHORT).show();
            return;
        }
        mPath = Environment.getExternalStorageDirectory().getAbsolutePath();
        rootPath = Environment.getExternalStorageDirectory().getAbsolutePath();
        mFilter = new FileSelectFilter(mFileTypes);
        mListFiles = getFileList(mPath);
        mAllFileAdapter = new AllFileAdapter(getContext(),mListFiles,mFilter);
        mRecyclerView.setAdapter(mAllFileAdapter);
    }

    private void initEvent() {
        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tempPath = new File(mPath).getParent();
                if (tempPath == null || mPath.equals(rootPath)) {
                    Toast.makeText(getContext(),"最外层了",Toast.LENGTH_SHORT).show();
                    return;
                }
                mPath = tempPath;
                mListFiles = getFileList(mPath);
                mAllFileAdapter.updateListData(mListFiles);
                mAllFileAdapter.notifyItemRangeChanged(0, mListFiles.size());
            }
        });
        mAllFileAdapter.setOnItemClickListener(new OnFileItemClickListener() {
            @Override
            public void click(int position) {
                FileEntity entity = mListFiles.get(position);
                //如果是文件夹点击进入文件夹
                if (entity.getFile().isDirectory()) {
                    getIntoChildFolder(position);
                }else {
                    File file = entity.getFile();
                    ArrayList<FileEntity> files = PickerManager.getInstance().files;
                    if(files.contains(entity)){
                        files.remove(entity);
                        if(mOnUpdateDataListener!=null){
                            mOnUpdateDataListener.update(-file.length());
                        }
                        entity.setSelected(!entity.isSelected());
                        mAllFileAdapter.notifyItemChanged(position);
                    }else {
                        if(PickerManager.getInstance().files.size()<PickerManager.getInstance().maxCount){
                            files.add(entity);
                            if(mOnUpdateDataListener!=null){
                                mOnUpdateDataListener.update(file.length());
                            }
                            entity.setSelected(!entity.isSelected());
                            mAllFileAdapter.notifyItemChanged(position);
                        }else {
                            Toast.makeText(getContext(),getString(R.string.file_select_max,PickerManager.getInstance().maxCount),Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
    }
    //进入子文件夹
    private void getIntoChildFolder(int position) {
        mPath = mListFiles.get(position).getFile().getAbsolutePath();
        //更新数据源
        mListFiles = getFileList(mPath);
        mAllFileAdapter.updateListData(mListFiles);
        mAllFileAdapter.notifyDataSetChanged();
        mRecyclerView.scrollToPosition(0);
    }

    /**
     * 根据地址获取当前地址下的所有目录和文件，并且排序
     *
     * @param path
     * @return List<File>
     */
    private List<FileEntity> getFileList(String path) {
        List<FileEntity> fileListByDirPath = FileUtils.getFileListByDirPath(path, mFilter);
        if(fileListByDirPath.size()>0){
            mEmptyView.setVisibility(View.GONE);
        }else {
            mEmptyView.setVisibility(View.VISIBLE);
        }
        return fileListByDirPath;
    }
}
