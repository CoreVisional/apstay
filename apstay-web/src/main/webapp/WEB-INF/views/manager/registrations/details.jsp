<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="manager" tagdir="/WEB-INF/tags/manager" %>
<%@ taglib prefix="shared" tagdir="/WEB-INF/tags/shared" %>
<%@ taglib prefix="f" uri="/tlds/functions" %>

<manager:layout>
    <div class="d-sm-flex align-items-center justify-content-between mb-4">
        <h3 class="h3 mb-0 text-gray-800">Account Registration Details</h3>
        <c:if test="${registration.status() eq 'PENDING'}">
            <div>
                <button type="button" class="d-sm-inline-block btn btn-success shadow-sm mr-2" 
                        data-toggle="modal" data-target="#approveModal">
                    <i class="fa fa-check"></i> Approve
                </button>
                <button type="button" class="d-sm-inline-block btn btn-danger shadow-sm"
                        data-toggle="modal" data-target="#rejectModal">
                    <i class="fa fa-times"></i> Reject
                </button>
            </div>

            <div class="modal fade" id="approveModal" tabindex="-1" role="dialog" aria-labelledby="approveModalLabel" aria-hidden="true">
              <div class="modal-dialog" role="document">
                <div class="modal-content">
                  <form action="${pageContext.request.contextPath}/manager/registrations/approve" method="POST">
                    <div class="modal-header">
                      <h5 class="modal-title" id="approveModalLabel">Approve Registration</h5>
                      <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                      </button>
                    </div>
                    <div class="modal-body">
                      <input type="hidden" name="registrationId" value="${registration.id()}"/>
                      <p>Are you sure you want to approve the registration for <strong>${registration.name()}</strong>?</p>
                      <p>This will assign them to unit <strong>${registration.unitName()}</strong>.</p>
                    </div>
                    <div class="modal-footer">
                      <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
                      <button type="submit" class="btn btn-success">Approve</button>
                    </div>
                  </form>
                </div>
              </div>
            </div>

            <div class="modal fade" id="rejectModal" tabindex="-1" role="dialog" aria-labelledby="rejectModalLabel" aria-hidden="true">
              <div class="modal-dialog" role="document">
                <div class="modal-content">
                  <form action="${pageContext.request.contextPath}/manager/registrations/reject" method="POST">
                    <div class="modal-header">
                      <h5 class="modal-title" id="rejectModalLabel">Reject Registration</h5>
                      <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                      </button>
                    </div>
                    <div class="modal-body">
                      <input type="hidden" name="registrationId" value="${registration.id()}"/>
                      <div class="form-group">
                        <label for="remarks">Rejection Reason:</label>
                        <textarea name="remarks" id="remarks" class="form-control"></textarea>
                      </div>
                    </div>
                    <div class="modal-footer">
                      <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
                      <button type="submit" class="btn btn-danger">Reject</button>
                    </div>
                  </form>
                </div>
              </div>
            </div>
        </c:if>
    </div>

    <hr />

    <div>
        <dl class="row">
            <dt class="col-sm-2">Full Legal Name</dt>
            <dd class="col-sm-10">${registration.name()}</dd>
            
            <dt class="col-sm-2">Reviewed By</dt>
            <dd class="col-sm-10"><c:out value="${registration.reviewerName()}" default="-"/></dd>
            
            <dt class="col-sm-2">Identity Number</dt>
            <dd class="col-sm-10">${registration.identityNumber()}</dd>
            
            <dt class="col-sm-2">Email Address</dt>
            <dd class="col-sm-10">${registration.email()}</dd>
            
            <dt class="col-sm-2">Gender</dt>
            <dd class="col-sm-10">${registration.gender()}</dd>
            
            <dt class="col-sm-2">Phone Number</dt>
            <dd class="col-sm-10">${registration.phone()}</dd>
            
            <dt class="col-sm-2">Address</dt>
            <dd class="col-sm-10">${registration.address()}</dd>
            
            <dt class="col-sm-2">Hostel Unit</dt>
            <dd class="col-sm-10">${registration.unitName()}</dd>
            
            <dt class="col-sm-2">Submitted On</dt>
            <dd class="col-sm-10">${f:formatLocalDateTime(registration.createdAt(), 'MM/dd/yyyy hh:mm a')}</dd>
            
            <dt class="col-sm-2">Status</dt>
            <dd class="col-sm-10">${registration.status()}</dd>
            
            <dt class="col-sm-2">Remarks</dt>
            <dd class="col-sm-10"><c:out value="${registration.remarks()}" default="-"/></dd>
        </dl>
    </div>
</manager:layout>
